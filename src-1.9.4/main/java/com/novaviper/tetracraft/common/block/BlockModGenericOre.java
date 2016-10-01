package com.novaviper.tetracraft.common.block;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.novaviper.tetracraft.common.init.ModBlocks;
import com.novaviper.tetracraft.common.init.ModItems;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Generic function class for the mod's ores
 */
public class BlockModGenericOre extends BlockModGenericBlock
{
	public BlockModGenericOre(String name, CreativeTabs tab, float hardness, float resistance, float light, SoundType sound)
	{
		super(name, tab, Material.ROCK, hardness, resistance, light, sound);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		//return this == ModBlocks.nileCoalOre ? ModItems.nileCoal : (this == ModBlocks.nileGrainOre ? ModItems.nileGrain : Item.getItemFromBlock(this));
		return this == ModBlocks.triaxOre ? ModItems.triaxIngot : Item.getItemFromBlock(this);
	}
	/**
	 * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
	 */
	@Override
	public int quantityDroppedWithBonus(int par1, Random par2Random)
	{
		return this.quantityDropped(par2Random) + par2Random.nextInt(2);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1 + par1Random.nextInt(3);
	}

	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune){
		super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
		{
			int j = 0;

			if (this == ModBlocks.triaxOre)
			{
				j = MathHelper.getRandomIntegerInRange(rand, 0, 2);
			}

			return j;
		}
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
	{
		for (int l = 0; l < 5; ++l)
		{
			double d0 = (double)((float)pos.getX() + rand.nextFloat());
			double d1 = (double)((float)pos.getY() + rand.nextFloat());
			double d2 = (double)((float)pos.getZ() + rand.nextFloat());
			double d3 = 0.0D;
			double d4 = 0.0D;
			double d5 = 0.0D;
			int i1 = rand.nextInt(2) * 2 - 1;
			d3 = ((double)rand.nextFloat() - 0.5D) * 1.9D;
			d4 = ((double)rand.nextFloat() - 0.5D) * 1.9D;
			d5 = ((double)rand.nextFloat() - 0.5D) * 1.9D;
			worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, d0, d1, d2, d3, d4, d5);
		}
	}
}

