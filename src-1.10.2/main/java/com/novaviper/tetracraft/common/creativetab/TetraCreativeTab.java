package com.novaviper.tetracraft.common.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import com.novaviper.tetracraft.common.init.ModItems;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Definition of the mod's {@link CreativeTabs}
 */
public class TetraCreativeTab extends CreativeTabs {

	public TetraCreativeTab(int id) {
		super("tetracraft");
		//this.setBackgroundImageName("item_search.png");
	}

	@Override
	public Item getTabIconItem() {
		return ModItems.triaxIngot;
	}

	/**
	 * Determines if the search bar should be shown for this tab.
	 *
	 * @return True to show the bar
	 */
	/*@Override
	public boolean hasSearchBar() {
		return getTabIndex() == this.getTabIndex();
	}*/

}
