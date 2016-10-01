package com.novaviper.tetracraft.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Generic function class for the mod's items
 */
public class ItemModGenericItem extends Item{

		public ItemModGenericItem(String name, CreativeTabs tab) {
			super();
			this.setItemName(this, name);
			this.setCreativeTab(tab);
		}

	/**
	 * Set the registry name of {@code item} to {@code itemName} and the unlocalised name to the full registry name.
	 *
	 * @param item     The item
	 * @param itemName The item's name
	 */
	public static void setItemName(Item item, String itemName) {
		item.setRegistryName(itemName);
		item.setUnlocalizedName(item.getRegistryName().toString());
	}
}
