package com.natamus.silkiertouch.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.silkiertouch.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean enableDragonEggDrop = true;
	@Entry public static boolean enableSpawnerDrop = true;
	@Entry public static boolean enableTrialSpawnerDrop = false;

	@Entry public static boolean enableFullCakeDrop = true;

	@Entry public static boolean enableBuddedAmethystDrop = true;
	@Entry public static boolean enableFarmlandDrop = true;
	@Entry public static boolean enableTallGrassDrop = true;
	@Entry public static boolean enableSnowLayerDrop = true;
	@Entry public static boolean enableVinesDrop = true;

	@Entry public static boolean enableInfestedStoneDrop = false;

	public static void initConfig() {
		configMetaData.put("enableDragonEggDrop", Arrays.asList(
			"Disables the dragon egg teleport functionality when using a silk touch pickaxe."
		));
		configMetaData.put("enableSpawnerDrop", Arrays.asList(
			"Enables monster spawners to drop with silk touch. It keeps the mob data."
		));
		configMetaData.put("enableTrialSpawnerDrop", Arrays.asList(
			"Lowers the trial spawner 'strength' to allow breaking it. Will be dropped with silk touch on a pickaxe. Changing this requires a reboot."
		));

		configMetaData.put("enableFullCakeDrop", Arrays.asList(
			"Allows cakes that haven't been eaten from to drop with silk touch. Works with candle cakes as well."
		));

		configMetaData.put("enableBuddedAmethystDrop", Arrays.asList(
			"Allows Budded Amethyst to drop when mined with silk touch."
		));
		configMetaData.put("enableFarmlandDrop", Arrays.asList(
			"Allows Farmland to drop when harvested with silk touch."
		));
		configMetaData.put("enableTallGrassDrop", Arrays.asList(
			"Allows Tall Grass to drop when harvested with silk touch."
		));
		configMetaData.put("enableSnowLayerDrop", Arrays.asList(
			"Allows Snow Layers to drop when harvested with silk touch."
		));
		configMetaData.put("enableVinesDrop", Arrays.asList(
			"Allows Vines to drop when harvested with silk touch."
		));

		configMetaData.put("enableInfestedStoneDrop", Arrays.asList(
			"Allows Infested Stone to drop when mined with silk touch."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}