package com.novaviper.tetracraft.lib;

import com.novaviper.tetracraft.lib.ModReference;
import net.minecraft.util.ResourceLocation;
import java.util.Hashtable;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Defines all textures for the mod
 */
public class TextureReference {

	private static Hashtable<String, ResourceLocation> bowStandbyTextures = new Hashtable<>();
	private static Hashtable<String, ResourceLocation> bowPullingTextures = new Hashtable<>();
	private static Hashtable<String, ResourceLocation> terrakonSkins = new Hashtable<>();

	/**
	 * Gets the Terrakon's textures
	 * @param type The state of the Terrakon
	 * @return The Terrakon texture of the given type
	 */
	public static ResourceLocation getTerrakonSkins(String type) {
		if (!terrakonSkins.containsKey(type)) {
			terrakonSkins.put(type, new ResourceLocation(ModReference.MOD_NAME, getEntityTexturePath("terrakon/" ,"terrakon" + type + ".png")));
		}
		return terrakonSkins.get(type);
	}

	/**
	 * Gets the standby textures for bows
	 * @param type The type of bow to search for
	 * @return The standby bow texture of the given type
	 */
	public static ResourceLocation getBowStandbyTextures(String type) {
		if (!bowStandbyTextures.containsKey(type)) {
			bowStandbyTextures.put(type, new ResourceLocation(ModReference.MOD_NAME, getItemTexturePath(type + "Bow_standby")));
		}
		return bowStandbyTextures.get(type);
	}

	/**
	 * Gets the pulling textures for bows
	 * @param type The type of bow to search for
	 * @param state The pulling state of the bow
	 * @return The given pulling texture state of the given type of bow
	 */
	public static ResourceLocation getBowPullingTextures(String type, String state) {
		if (!bowPullingTextures.containsKey(type)) {
			bowPullingTextures.put(type, new ResourceLocation(ModReference.MOD_NAME, getItemTexturePath(type + "Bow_pulling_" + state + "")));
		}
		return bowPullingTextures.get(type);
	}


	//@formatter:off
	/*==============================================TEXTURE PATHS==============================================*/
	/**
	 * Gets a local GUI texture file path.
	 * @param textureFileName The .png file that relates to the texture file.
	 * @return The whole path string including the given parameter.
	 */
	private static String getItemTexturePath(String textureFileName) {
		return String.format("%s/items/%s", getOverrideTexturesPath(), textureFileName);
	}

	/**
	 * Gets a local GUI texture file path.
	 * @param textureFileName The .png file that relates to the texture file.
	 * @return The whole path string including the given parameter.
	 */
	private static String getGuiTexturePath(String textureFileName) {
		return String.format("%s/gui/%s", getOverrideTexturesPath(), textureFileName);
	}

	/**
	 * Gets a local entity texture file path.
	 * @param entityFileName The location path of the textures for the entity (Can be in the entities folder or in it's own folder)
	 * @param textureFileName The .png file that relates to the texture file.
	 * @return The whole path string including the given parameter.
	 */
	private static String getEntityTexturePath(String entityFileName, String textureFileName) {
		return String.format("%s/entity/%s", getOverrideTexturesPath(), entityFileName + textureFileName);
	}

	/**
	 * Gets a local item/model texture file path.
	 * @param textureFileName The .png file that relates to the texture file.
	 * @return The whole path string including the given parameter.
	 */
	private static String getModelTexturePath(String textureFileName) {
		return String.format("%s/models/%s", getOverrideTexturesPath(), textureFileName);
	}

	/**
	 * Gets the location of the mods textures.
	 * @return The default texture local
	 */
	private static String getOverrideTexturesPath() {
		return "textures";
	}
}