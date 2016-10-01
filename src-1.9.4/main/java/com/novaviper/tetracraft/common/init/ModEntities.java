package com.novaviper.tetracraft.common.init;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import com.novaviper.cryolib.lib.Registers;
import com.novaviper.tetracraft.common.entity.*;
import com.novaviper.tetracraft.client.render.entity.*;
import com.novaviper.tetracraft.common.entity.properties.EntityTamed;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Main class for defining and loading all entities
 */
public class ModEntities {

	public static void load(){
		addEntityWithEgg(EntityTerrakon.class, "Terrakon", 1, 0x99ffcc, 0x009900);
		Registers.addEntitySpawn(EntityTerrakon.class, 80, 2, 4, EnumCreatureType.CREATURE, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.COLD_TAIGA, Biomes.COLD_TAIGA_HILLS, Biomes.PLAINS);
	}

	public static void loadProperties(){
		Registers.addProperty(new EntityTamed.Serializer());
	}

	public static void loadRenderers(){ //NAV
		Registers.addEntityRender(EntityTerrakon.class, new IRenderFactory<EntityTerrakon>() {
			@Override
			public Render<? super EntityTerrakon> createRenderFor(RenderManager manager) {
				return new RenderTerrakon(manager, 0.5F);
			}});
	}

	private static void addEntityWithEgg(Class entityClass, String name, int id, int eggColor, int eggSpotsColor){
		Registers.addEntityWithEgg(entityClass, name, id, eggColor, eggSpotsColor, TetraCraft.instance);
	}
}