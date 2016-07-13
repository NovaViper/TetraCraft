package com.novaviper.tetracraft.client.render.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.novaviper.tetracraft.lib.TextureReference;
import com.novaviper.tetracraft.client.render.entity.RenderTerrakon;
import com.novaviper.tetracraft.common.entity.EntityTerrakon;
import com.novaviper.cryolib.lib.Constants;

/**
 * Created by NovaViper on 3/16/2016.
 * Class Purpose:
 */
@SideOnly(Side.CLIENT)
public class LayerTerrakon implements LayerRenderer<EntityTerrakon>
{
	private final RenderTerrakon terrakonRenderer;

	public LayerTerrakon(RenderTerrakon terrakonRendererIn)
	{
		this.terrakonRenderer = terrakonRendererIn;
	}

	@Override
	public void doRenderLayer(EntityTerrakon entityTerrakon, float par2, float par3, float partialTicks, float par5, float par6, float par7, float scale)
	{
		if (!entityTerrakon.isInvisible()) {
			if (entityTerrakon.isTamed()) {
				this.terrakonRenderer.bindTexture(TextureReference.getTerrakonSkins("_collar"));
				EnumDyeColor enumdyecolor = EnumDyeColor.byMetadata(entityTerrakon.getCollarColor().getMetadata());
				float[] afloat = EntitySheep.getDyeRgb(enumdyecolor);
				GlStateManager.color(afloat[0], afloat[1], afloat[2]);
				this.terrakonRenderer.getMainModel().render(entityTerrakon, par2, par3, par5, par6, par7, scale);
			}
			if (entityTerrakon.isTamed() && entityTerrakon.isSaddled()) {
				this.terrakonRenderer.bindTexture(TextureReference.getTerrakonSkins("_saddle"));
				GlStateManager.color(1f, 1f, 1f);
				this.terrakonRenderer.getMainModel().render(entityTerrakon, par2, par3, par5, par6, par7, scale);
			}
			if (entityTerrakon.isTamed() && entityTerrakon.getHealth() <= Constants.LOW_HP) {
				this.terrakonRenderer.bindTexture(TextureReference.getTerrakonSkins("_dying"));
				GlStateManager.color(1f, 1f, 1f);
				this.terrakonRenderer.getMainModel().render(entityTerrakon, par2, par3, par5, par6, par7, scale);
			}
			if (entityTerrakon.getGender() == true) { //If the Terrakon is a boy, set the texture to the dark texture
				this.terrakonRenderer.bindTexture(TextureReference.getTerrakonSkins("_male"));
				GlStateManager.color(1f, 1f, 1f);
				this.terrakonRenderer.getMainModel().render(entityTerrakon, par2, par3, par5, par6, par7, scale);
			}
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return true;
	}
}
