package com.novaviper.tetracraft.common.init;

import net.minecraft.util.SoundEvent;
import com.novaviper.cryolib.lib.Registers;
import com.novaviper.tetracraft.lib.ModReference;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @author Choonster
 * @date 7/10/2016
 * @purpose Registers this mod's {@link SoundEvent}s.
 */
@SuppressWarnings("WeakerAccess")
public class ModSoundEvents {

	// Terrakon Sounds\\
	public static SoundEvent terrakonBark;
	public static SoundEvent terrakonGrowl;
	public static SoundEvent terrakonHurt;
	public static SoundEvent terrakonPanting;
	public static SoundEvent terrakonWhine;
	public static SoundEvent terrakonDeath;
	public static SoundEvent Saddle;
	public static SoundEvent Land;

	/**
	 * Register the {@link SoundEvent}s.
	 */
	public static void registerSounds() {
		terrakonBark = registerSound("entity.terrakon.bark");
		terrakonGrowl = registerSound("entity.terrakon.growl");
		terrakonHurt = registerSound("entity.terrakon.hurt");
		terrakonPanting = registerSound("entity.terrakon.pant");
		terrakonWhine = registerSound("entity.terrakon.whine");
		terrakonDeath = registerSound("entity.terrakon.death");
		Saddle = registerSound("action.saddle");
		//Land = registerSound("");
	}

	private static SoundEvent registerSound(String soundName) {
		return Registers.registerSound(ModReference.MOD_ID, soundName);
	}
}