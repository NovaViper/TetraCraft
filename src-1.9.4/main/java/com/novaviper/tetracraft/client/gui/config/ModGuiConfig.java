package com.novaviper.tetracraft.client.gui.config;

import com.novaviper.cryolib.lib.Strings;
import com.novaviper.tetracraft.common.handler.ConfigHandler;
import com.novaviper.tetracraft.lib.ModReference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NovaViper on 2/6/2016.
 * Class Purpose: Defines the mod's configuration GUI appearance
 */
public class ModGuiConfig extends GuiConfig {

	//@formatter:off
	public ModGuiConfig(GuiScreen guiScreen) {
		super(guiScreen, getConfigElements(), ModReference.MOD_ID, false, false, I18n.format(Strings.getGuiTitle(ModReference.MOD_ID, "config")));
	}

	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<>();
		//list.add(new DummyCategoryElement("function", Strings.getGuiName(ModReference.MOD_ID, "config", "function"), FunctionEntry.class));
		//list.add(new DummyCategoryElement("load", Strings.guiName + "load", LoadEntry.class));
		//list.add(new DummyCategoryElement("misc", Strings.guiName + "misc", MiscEntry.class));
		return list;
	}

	/**
	 * This custom list entry provides the General Settings entry on the
	 * Minecraft Forge Configuration screen. It extends the base Category
	 * entry class and defines the IConfigElement objects that will be used
	 * to build the child screen.
	 */
	public static class FunctionEntry extends CategoryEntry // Function Entry
	{
		public FunctionEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
			super(owningScreen, owningEntryList, prop);
		}

		@Override
		protected GuiScreen buildChildScreen() {

			List<IConfigElement> list = new ArrayList<IConfigElement>();
			// list.add(new DummyCategoryElement("terrain", "gui.config.terrain", TerrainEntry.class));
			list.addAll((new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_FUNCTION))).getChildElements());
			return new GuiConfig(this.owningScreen, list, this.owningScreen.modID, ConfigHandler.CATEGORY_FUNCTION, this.configElement.requiresWorldRestart() ||
					this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() ||
					this.owningScreen.allRequireMcRestart, I18n.format(Strings.getGuiName(ModReference.MOD_ID, "config", "function")), I18n.format(Strings.getGuiTooltip(ModReference.MOD_ID, "config", "function")));
		}
	}

	public static class LoadEntry extends CategoryEntry // Load Entry
	{
		public LoadEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
			super(owningScreen, owningEntryList, prop);
		}

		@Override
		protected GuiScreen buildChildScreen() {
			List<IConfigElement> list = new ArrayList<IConfigElement>();
			// list.add(new DummyCategoryElement("terrain", "gui.config.terrain", TerrainEntry.class));
			list.addAll((new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_LOAD))).getChildElements());

			return new GuiConfig(this.owningScreen, list, this.owningScreen.modID, ConfigHandler.CATEGORY_LOAD, this.configElement.requiresWorldRestart() ||
					this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() ||
					this.owningScreen.allRequireMcRestart, I18n.format(Strings.getGuiName(ModReference.MOD_ID, "config", "load")), I18n.format(Strings.getGuiTooltip(ModReference.MOD_ID, "config", "function")));
		}
	}

	public static class MiscEntry extends CategoryEntry // Miscellaneous Entry
	{
		public MiscEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
			super(owningScreen, owningEntryList, prop);
		}

		@Override
		protected GuiScreen buildChildScreen() {
			List<IConfigElement> list = new ArrayList<IConfigElement>();
			list.add(new DummyCategoryElement("dimensions", Strings.getGuiName(ModReference.MOD_ID, "config", "dimensions"), DimensionsEntry.class));
			list.add(new DummyCategoryElement("biomes", Strings.getGuiName(ModReference.MOD_ID, "config", "biomes"), BiomesEntry.class));
			list.addAll((new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_MISC))).getChildElements());

			return new GuiConfig(this.owningScreen, list, this.owningScreen.modID, ConfigHandler.CATEGORY_MISC, this.configElement.requiresWorldRestart() ||
					this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() ||
					this.owningScreen.allRequireMcRestart, I18n.format(Strings.getGuiName(ModReference.MOD_ID, "config", "misc"), I18n.format(Strings.getGuiTooltip(ModReference.MOD_ID, "config", "misc"))));
		}
	}

	public static class DimensionsEntry extends CategoryEntry // Miscellaneous Entry
	{
		public DimensionsEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
			super(owningScreen, owningEntryList, prop);
		}

		@Override
		protected GuiScreen buildChildScreen() {
			return new GuiConfig(this.owningScreen, (new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_DIMENSION)).getChildElements()), this.owningScreen.modID, ConfigHandler.CATEGORY_DIMENSION, this.configElement.requiresWorldRestart() ||
					this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() ||
					this.owningScreen.allRequireMcRestart, I18n.format(Strings.getGuiName(ModReference.MOD_ID, "config", "dimensions")), I18n.format(Strings.getGuiTooltip(ModReference.MOD_ID, "config", "dimensions")));
		}
	}

	public static class BiomesEntry extends CategoryEntry // Miscellaneous Entry
	{
		public BiomesEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
			super(owningScreen, owningEntryList, prop);
		}

		@Override
		protected GuiScreen buildChildScreen() {

			return new GuiConfig(this.owningScreen, (new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_BIOME)).getChildElements()), this.owningScreen.modID, ConfigHandler.CATEGORY_BIOME, this.configElement.requiresWorldRestart() ||
					this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() ||
					this.owningScreen.allRequireMcRestart, I18n.format(Strings.getGuiName(ModReference.MOD_ID, "config", "biomes")), I18n.format(Strings.getGuiTooltip(ModReference.MOD_ID, "config", "biomes")));
		}
	}
}