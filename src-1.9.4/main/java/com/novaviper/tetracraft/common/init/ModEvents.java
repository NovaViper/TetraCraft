package com.novaviper.tetracraft.common.init;

import com.novaviper.cryolib.lib.Registers;
import com.novaviper.tetracraft.common.events.ItemEvents;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Main class for defining and loading all events
 */
public class ModEvents {

	public static void load(){

		Registers.addEvent(new ItemEvents());
		//Registers.addEvent(new EntityEvents());
	}
}
