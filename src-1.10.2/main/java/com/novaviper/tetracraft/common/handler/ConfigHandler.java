package com.novaviper.tetracraft.common.handler;

import com.novaviper.cryolib.lib.Registers;
import com.novaviper.tetracraft.lib.ModReference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Declaration class for configurations
 */
public class ConfigHandler {

	// Add more Categories for GuiFactory\\
	public static Configuration config;
	public static final String CATEGORY_LOAD = "load";
	public static final String CATEGORY_FUNCTION = "function";
	public static final String CATEGORY_MISC = "miscellaneous";
	public static final String CATEGORY_DIMENSION = "dimensions";
	public static final String CATEGORY_BIOME = "biomes";

	@SideOnly(Side.CLIENT)
	public static void init(File file) {
		config = new Configuration(file);
		loadConfig();
		Registers.addEvent(new ConfigHandler());
	}

	public static void loadConfig() {
		config.addCustomCategoryComment(CATEGORY_FUNCTION, "Here you can manage all mod and entity functions");
		config.addCustomCategoryComment(CATEGORY_LOAD, "Here you can manage what the mod loads into the game");
		config.addCustomCategoryComment(CATEGORY_MISC, "Here you can manage the miscellaneous functions of the mod");
		config.addCustomCategoryComment(CATEGORY_DIMENSION, "Here you can manage the mod's dimension factors");
		config.addCustomCategoryComment(CATEGORY_BIOME, "Here you can manage the mod's biome factors");

		//@formatter:off
		/*=================================Function Configurations==========================================*/
		//@formatter:on
		List<String> orderFunc = new ArrayList<String>();
		//Constants.modChecker = config.get(CATEGORY_FUNCTION, "ModCheck", Constants.modChecker).setLanguageKey(Strings.getGuiProperty(ModReference.modid, "config","ModCheck")).getBoolean(Constants.modChecker);
		orderFunc.add("ModCheck");

		config.setCategoryPropertyOrder(CATEGORY_FUNCTION, orderFunc);

		//@formatter:off
		/*=================================Load Configurations==========================================*/
		//@formatter:on
		List<String> orderLoad = new ArrayList<String>();

		config.setCategoryPropertyOrder(CATEGORY_LOAD, orderLoad);

		//@formatter:off
		/*=================================Miscellaneous Configurations==========================================*/
		//@formatter:on
		List<String> orderMisc = new ArrayList<String>();

		config.setCategoryPropertyOrder(CATEGORY_MISC, orderMisc);
		//@formatter:off
		/*=================================DimensionsConfigurations==========================================*/
		//@formatter:on
		List<String> orderDimensions = new ArrayList<String>();

		config.setCategoryPropertyOrder(CATEGORY_DIMENSION, orderDimensions);

		//@formatter:off
		/*=================================BiomesConfigurations==========================================*/
		//@formatter:on
		List<String> orderBiomes = new ArrayList<String>();

		config.setCategoryPropertyOrder(CATEGORY_BIOME, orderBiomes);

		if (config.hasChanged()) {
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(ModReference.MOD_ID)) {
			loadConfig();
		}
	}
}