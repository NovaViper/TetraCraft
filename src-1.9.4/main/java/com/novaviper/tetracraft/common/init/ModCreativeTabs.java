package com.novaviper.tetracraft.common.init;

import net.minecraft.creativetab.CreativeTabs;
import com.novaviper.tetracraft.common.creativetab.TetraCreativeTab;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Main class for defining and loading all {@link CreativeTabs}
 */
public class ModCreativeTabs {

	public static CreativeTabs tetraTab;

	public static void load(){

		tetraTab = new TetraCreativeTab(CreativeTabs.getNextID());
	}
}