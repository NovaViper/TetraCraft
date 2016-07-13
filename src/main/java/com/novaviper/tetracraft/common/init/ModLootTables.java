package com.novaviper.tetracraft.common.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import com.novaviper.cryolib.lib.Registers;
import com.novaviper.tetracraft.lib.ModReference;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Defines and registers this mod's {@link LootTable}s.
 */
public class ModLootTables {
	public static final ResourceLocation LootTableTerrakon = registerLootTable("terrakon", "entities");

	private static ResourceLocation registerLootTable(String entityName, String id){
		return Registers.registerLootTable(ModReference.MOD_ID, entityName, id);
	}
}