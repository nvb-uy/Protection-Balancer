package elocindev.protbalancer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import elocindev.protbalancer.config.ProtectionBalancerConfig;

public class ProtBalancer implements ModInitializer {
	public static final String MODID = "protbalancer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static ProtectionBalancerConfig CONFIG = ProtectionBalancerConfig.INSTANCE;

	@Override
	public void onInitialize() {
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success)
		-> {
			NecConfigAPI.registerConfig(ProtectionBalancerConfig.class);
			CONFIG = ProtectionBalancerConfig.INSTANCE;
		});

		NecConfigAPI.registerConfig(ProtectionBalancerConfig.class);
		CONFIG = ProtectionBalancerConfig.INSTANCE;
	}
}