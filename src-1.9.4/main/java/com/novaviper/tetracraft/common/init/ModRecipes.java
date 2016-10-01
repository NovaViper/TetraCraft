package com.novaviper.tetracraft.common.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import com.novaviper.cryolib.lib.Registers;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Main class for defining and loading all recipes
 */
public class ModRecipes {
	public static void load() {
		Registers.addRecipe(new ItemStack(ModItems.ballisticBow),
				" AB",
				"C B",
				" AB",
				'A', new ItemStack(Items.STICK), 'B', Items.STRING, 'C', ModItems.triaxIngot );
		Registers.addRecipe(new ItemStack(ModItems.breedingBone, 2),
				" B ",
				"BAB",
				" B ",
				'A', new ItemStack(Items.BONE), 'B', ModItems.triaxIngot);
	}
}