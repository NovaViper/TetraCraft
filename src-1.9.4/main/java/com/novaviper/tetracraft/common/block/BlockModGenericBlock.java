package com.novaviper.tetracraft.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @author Choonster
 * @date 7/10/2016
 * @purpose Generic function class for the mod's blocks
 */
public class BlockModGenericBlock extends Block {

	public BlockModGenericBlock(String name, CreativeTabs tab, Material material, float hardness, float resistance, float light, SoundType sound)
	{
		super(material);
		this.setBlockName(this, name);
		this.setCreativeTab(tab);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setLightLevel(light);
		this.setSoundType(sound);
	}

	/**
	 * Set the registry name of {@code block} to {@code blockName} and the unlocalised name to the full registry name.
	 *
	 * @param block     The block
	 * @param blockName The block's name
	 */
	public static void setBlockName(Block block, String blockName) {
		block.setRegistryName(blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
