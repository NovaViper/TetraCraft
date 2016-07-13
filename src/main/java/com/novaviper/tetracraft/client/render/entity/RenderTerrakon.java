package com.novaviper.tetracraft.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.novaviper.tetracraft.client.model.ModelTerrakon;
import com.novaviper.tetracraft.client.render.entity.layers.LayerTerrakon;
import com.novaviper.tetracraft.common.entity.EntityTerrakon;
import com.novaviper.tetracraft.lib.TextureReference;

/**
 * Created by NovaViper on 3/1/2016.
 * Class Purpose:Rendering class for Terrakon
 */
@SideOnly(Side.CLIENT)
public class RenderTerrakon extends RenderLiving<EntityTerrakon>
{
	public RenderTerrakon(RenderManager renderManagerIn, float shadowSizeIn)
	{
		super(renderManagerIn, new ModelTerrakon(), shadowSizeIn);
		this.addLayer(new LayerTerrakon(this));
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityTerrakon entity)
	{
		return entity.isAngry() ? TextureReference.getTerrakonSkins("_angry") : TextureReference.getTerrakonSkins("");
	}
}