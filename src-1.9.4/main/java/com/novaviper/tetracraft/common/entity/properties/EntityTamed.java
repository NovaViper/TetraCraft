package com.novaviper.tetracraft.common.entity.properties;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.properties.EntityProperty;
import com.novaviper.tetracraft.common.entity.EntityModTameable;
import com.novaviper.tetracraft.lib.ModReference;

import java.util.Random;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Declares Entity Property for Tamed Mod Entities
 */
public class EntityTamed implements EntityProperty
{
	private final boolean isTamed;

	public EntityTamed(boolean isTamedIn)
	{
		this.isTamed = isTamedIn;
	}

	public boolean testProperty(Random random, Entity entityIn)
	{
		if(entityIn instanceof EntityModTameable){
			EntityModTameable entityTameable = (EntityModTameable)entityIn;
			if(entityTameable.isTamed()){
				return this.isTamed == true;
			}else{
				return this.isTamed == false;
			}
		}else{
			return this.isTamed == false;
		}
	}

	public static Serializer getSerializer(){
		return new Serializer();
	}

	public static class Serializer extends EntityProperty.Serializer<EntityTamed>
	{
		public Serializer()
		{
			super(new ResourceLocation(ModReference.MOD_ID, "is_tamed"), EntityTamed.class);
		}

		public JsonElement serialize(EntityTamed property, JsonSerializationContext serializationContext)
		{
			return new JsonPrimitive(Boolean.valueOf(property.isTamed));
		}

		public EntityTamed deserialize(JsonElement element, JsonDeserializationContext deserializationContext)
		{
			return new EntityTamed(JsonUtils.getBoolean(element, ModReference.MOD_ID + ":is_tamed"));
		}
	}
}