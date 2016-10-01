package com.novaviper.tetracraft.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Generic function class for the mod's rapid-fire items
 */
public class ItemContinousBow extends ItemModBow{
	int fireRate;

	public ItemContinousBow(String name, CreativeTabs tab, String type, int maxUsage, int fireRate) {
		super(name, tab, type, maxUsage);
		this.fireRate = fireRate;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 10;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		int charge = (getMaxItemUseDuration(stack) - timeLeft) * fireRate;
		fireArrow(stack, worldIn, entityLiving, charge);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		int charge = getMaxItemUseDuration(stack) * fireRate;
		fireArrow(stack, worldIn, entityLiving, charge);
		return stack;
	}
}
