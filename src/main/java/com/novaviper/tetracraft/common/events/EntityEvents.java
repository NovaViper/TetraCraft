package com.novaviper.tetracraft.common.events;

import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.novaviper.tetracraft.common.entity.EntityModTameable;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Defines events for entities
 */
public class EntityEvents {

	@SubscribeEvent
	public void onEntityCreate(EntityEvent.EntityConstructing event){
		if(event.getEntity() instanceof EntityModTameable){
			EntityModTameable entityModTameable = (EntityModTameable)event.getEntity();
		}
	}
}
