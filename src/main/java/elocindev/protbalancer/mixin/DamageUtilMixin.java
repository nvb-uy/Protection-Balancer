package elocindev.protbalancer.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import elocindev.protbalancer.ProtBalancer;
import elocindev.protbalancer.math.FormulaParser;
import net.minecraft.entity.DamageUtil;

@Mixin(DamageUtil.class)
public class DamageUtilMixin {

    @ModifyReturnValue(method = "getDamageLeft", at = @At("RETURN"))
    private static float getDamageLeft(float original, float damage, float armor, float armorToughness) {
        String formula = ProtBalancer.CONFIG.armor_formula;

        if (ProtBalancer.CONFIG.enable_armor_formula && !formula.isEmpty()) {
            formula = formula.replace("DAMAGE", String.valueOf(damage));
            formula = formula.replace("ARMOR_TOUGHNESS", String.valueOf(armorToughness));
            formula = formula.replace("ARMOR", String.valueOf(armor));

            return (float) (damage * (1.0F - FormulaParser.evaluateFormula(formula)));
        }

        return original;
    }
  
    @ModifyReturnValue(method = "getInflictedDamage", at = @At("RETURN"))
    private static float getInflictedDamage(float original, float damage, float protection) {
        String formula = ProtBalancer.CONFIG.protection_formula;

        if (ProtBalancer.CONFIG.enable_protection_formula && !formula.isEmpty()) {
            formula = formula.replace("DAMAGE", String.valueOf(damage));
            formula = formula.replace("PROTECTION", String.valueOf(protection));

            return (float) (damage * (1.0F - FormulaParser.evaluateFormula(formula)));
        }
        
        return original;
    }
}
