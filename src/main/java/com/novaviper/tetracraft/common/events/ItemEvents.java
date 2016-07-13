package com.novaviper.tetracraft.common.events;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.novaviper.tetracraft.common.item.ItemContinousBow;
import com.novaviper.tetracraft.common.item.ItemModBow;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Defines events for items
 */
public class ItemEvents {

	/***
	 * Defines the FOV Event for items such as bows
	 * @param event the FOV event
	 * @implNote see {@link AbstractClientPlayer} for FOV bow
	 */
	@SubscribeEvent
	public void FOVBowUpdate(FOVUpdateEvent event){
		EntityPlayer player = event.getEntity();
		if(player.isHandActive() && player.getActiveItemStack() != null){
			Item getItem = player.getActiveItemStack().getItem();
			if(getItem instanceof ItemModBow && !(getItem instanceof ItemContinousBow)){
				int useMaxCount = player.getItemInUseMaxCount();
				float v = (float)useMaxCount / 20.0F;

				if (v > 1.0F)
				{
					v = 1.0F;
				}
				else
				{
					v *= v;
				}

				event.setNewfov(event.getNewfov()*1.0F - v * 0.15F);
			}
		}
	}
}
