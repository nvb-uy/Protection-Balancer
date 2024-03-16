package elocindev.protbalancer.config;

import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import elocindev.necronomicon.config.Comment;
import elocindev.necronomicon.config.NecConfig;

public class ProtectionBalancerConfig {
    @NecConfig
    public static ProtectionBalancerConfig INSTANCE;

    public static String getFile() {
        return NecConfigAPI.getFile("protection_balancer.json5");
    }

    @Comment("--------------------------------------------------------------------------------------------------------")
	@Comment("                                  Protection Balancer by ElocinDev")
	@Comment("--------------------------------------------------------------------------------------------------------")
	@Comment(" ")
	@Comment(" This mod allows to add custom formulas to handle the armor and protection enchantment's damage")
	@Comment(" mitigation. This is useful to balance the game to your liking.")
	@Comment(" ")
	@Comment("--------------------------------------------------------------------------------------------------------")
	@Comment("Do not touch this")
	public int configVersion = 1;
	@Comment("--------------------------------------------------------------------------------------------------------")
	@Comment(" ")

    @Comment("--------------------------------------------------------------------------------------------------------")
	@Comment("                                         Formula Cheat Sheet")
	@Comment(" ")
	@Comment(" * You can use the following functions:")
	@Comment("    min(a, b) 			: Returns the smallest value between a and b")
	@Comment("    max(a, b) 			: Returns the largest value between a and b")
	@Comment("    sqrt(x) 			: Returns the square root of x")
	@Comment("    sin(x) 			: Returns the sine of x")
	@Comment("    cos(x) 			: Returns the cosine of x")
	@Comment("    tan(x) 			: Returns the tangent of x")
	@Comment("    clamp(x, min, max) : Returns x clamped between min and max")
	@Comment(" ")
	@Comment(" * Variables depend on the formula type, but both will have DAMAGE available.")
	@Comment(" ")
	@Comment(" * You can use the following operators:")
	@Comment("    +, -, *, /, ^")
	@Comment("--------------------------------------------------------------------------------------------------------")
	@Comment(" ")

	@Comment("--------------------------------------------------------------------------------------------------------")
	@Comment("                                       Armor Handling Formula")
	@Comment(" ")
	@Comment(" The result of this formula will be the total damage reduced")
	@Comment(" ")
	@Comment(" Variables:")
	@Comment("   DAMAGE : The damage of the current damage instance")
	@Comment("   ARMOR : The armor value of the victim")
	@Comment("   ARMOR_TOUGHNESS : The armor toughness value of the victim")
	@Comment(" ")
	@Comment(" By the mod's default, the formula is: (min(ARMOR / 60.0, 1.0) * 0.15)")
	@Comment(" This formula will mitigate up to 15% of the damage at 60 of armor")
	@Comment(" ")
	@Comment(" You can define a more complex formula that fits for you, as this was designed for the Prominence modpack.")
	@Comment("--------------------------------------------------------------------------------------------------------")
	public boolean enable_armor_formula = true;
	public String armor_formula = "min(ARMOR / 60.0, 1.0) * 0.15";
	@Comment("--------------------------------------------------------------------------------------------------------")
	@Comment(" ")
	@Comment("--------------------------------------------------------------------------------------------------------")
	@Comment("                               Protection Enchantment Handling Formula")
	@Comment(" ")
	@Comment(" The result of this formula will be the total mitigation factor")
	@Comment(" A factor of 0.10 means 10% of the damage will be mitigated")
	@Comment(" ")
	@Comment(" Available Variables:")
	@Comment("   DAMAGE : The value of the current damage instance")
	@Comment("   PROTECTION : The total protection levels of the victim")
	@Comment(" ")
	@Comment(" By the mod's default, the formula is: (min(PROTECTION / 20.0, 1.0) * 0.10)")
	@Comment(" This formula will mitigate up to 10% of the damage at 20 protection levels (Full set of Prot V)")
	@Comment(" ")
	@Comment(" You can define a more complex formula that fits for you, as this was designed for the Prominence modpack.")
	@Comment("--------------------------------------------------------------------------------------------------------")
	public boolean enable_protection_formula = true;
	public String protection_formula = "min(PROTECTION / 20.0, 1.0) * 0.10";
}
