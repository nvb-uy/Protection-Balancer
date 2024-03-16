package elocindev.protbalancer.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elocindev.protbalancer.ProtBalancer;
import elocindev.protbalancer.math.FormulaParser;
import net.minecraft.entity.DamageUtil;

@Mixin(DamageUtil.class)
public class DamageUtilMixin {

    @Inject(method = "getDamageLeft", at = @At("HEAD"), cancellable = true)
    private static void getDamageLeft(float damage, float armor, float armorToughness, CallbackInfoReturnable<Float> ci) {
        String formula = ProtBalancer.CONFIG.armor_formula;

        if (ProtBalancer.CONFIG.enable_armor_formula && !formula.isEmpty()) {
            formula = formula.replace("DAMAGE", String.valueOf(damage));
            formula = formula.replace("ARMOR", String.valueOf(armor));
            formula = formula.replace("ARMOR_TOUGHNESS", String.valueOf(armorToughness));

            ci.setReturnValue(damage * (1.0F - (float) FormulaParser.evaluateFormula(formula)));
        }
    }
  
    @Inject(method = "getInflictedDamage", at = @At("HEAD"), cancellable = true)
    private static void getInflictedDamage(float damageDealt, float protection, CallbackInfoReturnable<Float> ci) {
        String formula = ProtBalancer.CONFIG.protection_formula;

        if (ProtBalancer.CONFIG.enable_protection_formula && !formula.isEmpty()) {
            formula = formula.replace("DAMAGE", String.valueOf(damageDealt));
            formula = formula.replace("PROTECTION", String.valueOf(protection));

            ci.setReturnValue(damageDealt * (1.0F - (float) FormulaParser.evaluateFormula(formula)));
        }
    }
}
