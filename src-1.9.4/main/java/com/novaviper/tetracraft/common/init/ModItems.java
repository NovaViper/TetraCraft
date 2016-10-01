package com.novaviper.tetracraft.common.init;

import net.minecraft.item.Item;
import com.novaviper.cryolib.lib.Registers;
import com.novaviper.tetracraft.common.item.ItemContinousBow;
import com.novaviper.tetracraft.common.item.ItemModGenericItem;
import com.novaviper.tetracraft.lib.ModReference;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Main class for defining and loading all items
 */
public class ModItems {

	public static Item ballisticBow;
	public static Item triaxIngot;
	public static Item breedingBone;

	public static void load(){
		ballisticBow = Registers.addItem(new ItemContinousBow("ballisticBow", ModCreativeTabs.tetraTab, "ballistic", 500, 10));
		triaxIngot = Registers.addItem(new ItemModGenericItem("triaxIngot", ModCreativeTabs.tetraTab));
		breedingBone = Registers.addItem(new ItemModGenericItem("breedingBone", ModCreativeTabs.tetraTab));
	}

	public static void loadRenderersAndVariants(){
		addItemRender(ballisticBow, 0, "ballisticBow");
		addItemRender(triaxIngot, 0, "triaxIngot");
		addItemRender(breedingBone, 0, "breedingBone");
	}

	public static void addItemRender(Item item, int metadata, String itemString){
		Registers.addItemRender(ModReference.MOD_ID, item, metadata, itemString);
	}
}