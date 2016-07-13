package com.novaviper.tetracraft.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import com.novaviper.cryolib.lib.Registers;
import com.novaviper.tetracraft.common.block.BlockModGenericOre;
import com.novaviper.tetracraft.lib.ModReference;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Main class for defining and loading all blocks
 */
public class ModBlocks {

	//public static Block broxStone;
	public static Block triaxOre;

	public static void load(){
		//broxStone = Registers.addBlock(new BlockModGenericBlock("broxStone", ModCreativeTabs.tetraTab, Material.ROCK, 2.0f, 2.0f, 0.0f, Block.soundTypeStone));
		triaxOre =  Registers.addBlock(new BlockModGenericOre("triaxOre", ModCreativeTabs.tetraTab, 1.5f, 0.0f, 0.0f, SoundType.STONE));
	}

	public static void loadRenderers(){
		//Registers.addBlockRender(broxStone, 0, "broxStone", "inventory");
		addBlockRender(triaxOre, 0, "triaxOre", "inventory");
	}

	private static void addBlockRender(Block block, int metadata, String blockString, String location){
		Registers.addBlockRender(ModReference.MOD_ID, block, metadata, blockString, location);
	}
}