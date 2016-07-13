package com.novaviper.tetracraft.client.gui.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

/**
 * Created by NovaViper on 2/6/2016.
 * Class Purpose: Initializes the mod's Configuration GUI Factory
 */
public class ConfigGuiFactory implements IModGuiFactory {
	// Get Examples from ForgeGuiFactory
	//Properties are in ConfigHandler
	//@formatter:off

	@Override
	public void initialize(Minecraft minecraftInstance) {}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return ModGuiConfig.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}
}