package com.novaviper.tetracraft.common.world.gen;

import com.novaviper.tetracraft.common.init.ModBlocks;
import com.novaviper.tetracraft.common.init.ModItems;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/11/2016
 * @purpose Defines the ores to be added to the {@link OreDictionary}
 */
public class ModOreDictionary {

	public static void loadOre(){
		OreDictionary.registerOre("oreTriax", ModBlocks.triaxOre);
		OreDictionary.registerOre("ignotTriax", ModItems.triaxIngot);
	}
}
