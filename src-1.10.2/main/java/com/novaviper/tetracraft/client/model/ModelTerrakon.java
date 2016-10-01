package com.novaviper.tetracraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import com.novaviper.tetracraft.common.entity.EntityTerrakon;

/**
 * Created by NovaViper on 2/23/2016 using Tabula 5.1.0.
 * Class Purpose: Model class for Terrakon
 */
public class ModelTerrakon extends ModelBase {
	public ModelRenderer LeftEar1;
	public ModelRenderer LeftEar2;
	public ModelRenderer RightEar1;
	public ModelRenderer RightEar2;
	public ModelRenderer Snout;
	public ModelRenderer Jaw;
	public ModelRenderer Head;
	public ModelRenderer Neck;
	public ModelRenderer Mane2;
	public ModelRenderer Mane1;
	public ModelRenderer LeftFrontLeg;
	public ModelRenderer RightFrontLeg;
	public ModelRenderer Torso;
	public ModelRenderer LeftBackLeg1;
	public ModelRenderer LeftBackLeg2;
	public ModelRenderer LeftBackLeg3;
	public ModelRenderer RightBackLeg1;
	public ModelRenderer RightBackLeg2;
	public ModelRenderer RightBackLeg3;
	public ModelRenderer Tail1;
	public ModelRenderer Tail2;
	public ModelRenderer Tail3;
	public ModelRenderer SaddleBase;
	public ModelRenderer SaddleUpper;
	public ModelRenderer SaddleLower;
	public ModelRenderer SaddleStrap1;
	public ModelRenderer SaddleStrap2;
	public ModelRenderer SaddleMetal1;
	public ModelRenderer SaddleMetal2;

	public float[] idle;

	public ModelTerrakon() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		//Body\\ NAV
		this.Neck = new ModelRenderer(this, 0, 19);
		this.Neck.setRotationPoint(-0.5F, 0.5F, -5.5F);
		this.Neck.addBox(-2.5F, -3.0F, -6.5F, 5, 6, 7, 0.0F);
		this.setRotateAngle(Neck, -0.16615534478986016F, 0.0F, 0.0F);
		this.Mane2 = new ModelRenderer(this, 67, 0);
		this.Mane2.setRotationPoint(0.5F, 0.0F, -5.5F);
		this.Mane2.addBox(-3.5F, -3.5F, -6.0F, 6, 9, 6, 0.0F);
		this.setRotateAngle(Mane2, -0.3113667385557882F, 0.0F, 0.0F);
		this.Mane1 = new ModelRenderer(this, 35, 0);
		this.Mane1.setRotationPoint(-0.5F, 0.0F, 1.0F);
		this.Mane1.addBox(-3.5F, -3.5F, -8.0F, 7, 9, 8, 0.0F);
		this.setRotateAngle(Mane1, -0.18203784098300857F, 0.0F, 0.0F);
		this.Torso = new ModelRenderer(this, 0, 0);
		this.Torso.setRotationPoint(0.0F, 9.0F, 0.0F);
		this.Torso.addBox(-3.5F, -3.5F, 0.0F, 6, 7, 11, 0.0F);
		this.setRotateAngle(Torso, -0.16109388995907695F, 0.0F, 0.0F);
		//Head\\ NAV
		this.Head = new ModelRenderer(this, 0, 34);
		this.Head.setRotationPoint(0.0F, -3.0F, -4.0F);
		this.Head.addBox(-3.5F, -3.5F, -9.5F, 7, 8, 8, 0.0F);
		this.setRotateAngle(Head, 0.819606616736537F, 0.0F, 0.0F);
		this.Snout = new ModelRenderer(this, 0, 51);
		this.Snout.setRotationPoint(0.0F, 3.5F, -9.0F);
		this.Snout.addBox(-2.0F, -3.0F, -7.0F, 4, 3, 7, 0.0F);
		this.Jaw = new ModelRenderer(this, 25, 54);
		this.Jaw.setRotationPoint(0.0F, 3.5F, -9.0F);
		this.Jaw.addBox(-2.0F, 0.0F, -6.0F, 4, 1, 7, 0.0F);
		//Ears\\ NAV
		this.LeftEar1 = new ModelRenderer(this, 16, 51);
		this.LeftEar1.setRotationPoint(-2.0F, -3.0F, -5.0F);
		this.LeftEar1.addBox(-1.5F, -5.0F, 0.1F, 3, 5, 1, 0.0F);
		this.setRotateAngle(LeftEar1, 0.10890854532444616F, -0.060562925044203235F, -0.10890854532444616F);
		this.LeftEar2 = new ModelRenderer(this, 25, 51);
		this.LeftEar2.setRotationPoint(0.0F, -5.0F, 0.1F);
		this.LeftEar2.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(LeftEar2, 0.14521139376592823F, 0.0F, 0.0F);
		this.RightEar1 = new ModelRenderer(this, 16, 51);
		this.RightEar1.setRotationPoint(2.0F, -3.0F, -5.0F);
		this.RightEar1.addBox(-1.5F, -5.0F, 0.1F, 3, 5, 1, 0.0F);
		this.setRotateAngle(RightEar1, 0.10890854532444616F, 0.060562925044203235F, 0.10890854532444616F);
		this.RightEar2 = new ModelRenderer(this, 25, 51);
		this.RightEar2.setRotationPoint(0.0F, -5.0F, 0.1F);
		this.RightEar2.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(RightEar2, 0.14521139376592823F, 0.0F, 0.0F);
		//Tail\\ NAV
		this.Tail1 = new ModelRenderer(this, 93, 0);
		this.Tail1.setRotationPoint(-0.5F, -0.5F, 10.5F);
		this.Tail1.addBox(-2.5F, -2.5F, 0.0F, 5, 5, 9, 0.0F);
		this.setRotateAngle(Tail1, -0.09110618695410395F, 0.0F, 0.0F);
		this.Tail2 = new ModelRenderer(this, 80, 16);
		this.Tail2.setRotationPoint(0.5F, 0.4F, 8.5F);
		this.Tail2.addBox(-2.5F, -2.5F, 0.0F, 4, 4, 9, 0.0F);
		this.setRotateAngle(Tail2, -0.0595157274930067F, 0.0F, 0.0F);
		this.Tail3 = new ModelRenderer(this, 104, 21);
		this.Tail3.setRotationPoint(0.5F, 0.4F, 8.5F);
		this.Tail3.addBox(-2.5F, -2.5F, 0.0F, 3, 3, 9, 0.0F);
		this.setRotateAngle(Tail3, -0.08429940287132605F, 0.0F, 0.0F);
		//FrontLegs\\ NAV
		this.LeftFrontLeg = new ModelRenderer(this, 31, 20);
		this.LeftFrontLeg.setRotationPoint(-4.0F, 9.0F, -5.0F);
		this.LeftFrontLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 15, 3, 0.0F);
		this.setRotateAngle(LeftFrontLeg, 0.0F, 0.0F, -6.200655107570901E-17F);
		this.RightFrontLeg = new ModelRenderer(this, 31, 20);
		this.RightFrontLeg.setRotationPoint(3.0F, 9.0F, -5.0F);
		this.RightFrontLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 15, 3, 0.0F);
		this.setRotateAngle(RightFrontLeg, 0.0F, 0.0F, -6.200655107570901E-17F);
		//BackLegs\\ NAV
		this.LeftBackLeg1 = new ModelRenderer(this, 45, 20);
		this.LeftBackLeg1.setRotationPoint(-3.0F, 7.6F, 8.6F);
		this.LeftBackLeg1.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 4, 0.0F);
		this.setRotateAngle(LeftBackLeg1, -0.4527384079673295F, 0.0F, 0.0F);
		this.LeftBackLeg2 = new ModelRenderer(this, 45, 32);
		this.LeftBackLeg2.setRotationPoint(0.0F, 5.8F, -0.6F);
		this.LeftBackLeg2.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(LeftBackLeg2, 0.9534733703645022F, 0.0F, 0.0F);
		this.LeftBackLeg3 = new ModelRenderer(this, 45, 42);
		this.LeftBackLeg3.setRotationPoint(0.0F, 5.2F, 0.2F);
		this.LeftBackLeg3.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3, 0.0F);
		this.setRotateAngle(LeftBackLeg3, -0.48415433450322704F, 0.0F, 0.0F);
		this.RightBackLeg1 = new ModelRenderer(this, 45, 20);
		this.RightBackLeg1.setRotationPoint(2.0F, 7.6F, 8.6F);
		this.RightBackLeg1.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 4, 0.0F);
		this.setRotateAngle(RightBackLeg1, -0.4527384079673338F, 0.0F, 0.0F);
		this.RightBackLeg2 = new ModelRenderer(this, 45, 32);
		this.RightBackLeg2.setRotationPoint(0.0F, 5.8F, -0.6F);
		this.RightBackLeg2.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(RightBackLeg2, 0.9534733703645022F, 0.0F, 0.0F);
		this.RightBackLeg3 = new ModelRenderer(this, 45, 42);
		this.RightBackLeg3.setRotationPoint(0.0F, 5.2F, 0.2F);
		this.RightBackLeg3.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3, 0.0F);
		this.setRotateAngle(RightBackLeg3, -0.48415433450322704F, 0.0F, 0.0F);
		//Saddle\\ NAV
		this.SaddleBase = new ModelRenderer(this, 59, 32);
		this.SaddleBase.setRotationPoint(-0.5F, -4.2F, 2.0F);
		this.SaddleBase.addBox(-3.0F, 0.0F, -3.0F, 6, 1, 8, 0.0F);
		this.SaddleUpper = new ModelRenderer(this, 58, 33);
		this.SaddleUpper.setRotationPoint(-0.0F, -0.3F, -3.0F);
		this.SaddleUpper.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
		this.SaddleLower = new ModelRenderer(this, 62, 27);
		this.SaddleLower.setRotationPoint(-0.0F, -0.6F, 4.0F);
		this.SaddleLower.addBox(-3.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.SaddleStrap1 = new ModelRenderer(this, 80, 32);
		this.SaddleStrap1.setRotationPoint(3.0F, 0.0F, 1.0F);
		this.SaddleStrap1.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
		this.setRotateAngle(SaddleStrap1, 0.12618730491919003F, 0.0F, 0.0F);
		this.SaddleStrap2 = new ModelRenderer(this, 80, 32);
		this.SaddleStrap2.setRotationPoint(-3.0F, 0.0F, 1.0F);
		this.SaddleStrap2.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
		this.setRotateAngle(SaddleStrap2, 0.12618730491919003F, 0.0F, 0.0F);
		this.SaddleMetal2 = new ModelRenderer(this, 59, 36);
		this.SaddleMetal2.setRotationPoint(0.0F, 5.7F, -0.5F);
		this.SaddleMetal2.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 2, 0.0F);
		this.SaddleMetal1 = new ModelRenderer(this, 59, 36);
		this.SaddleMetal1.setRotationPoint(0.0F, 5.7F, -0.5F);
		this.SaddleMetal1.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 2, 0.0F);

		this.LeftBackLeg1.addChild(this.LeftBackLeg2);
		this.Head.addChild(this.Jaw);
		this.RightBackLeg1.addChild(this.RightBackLeg2);
		this.Tail2.addChild(this.Tail3);
		this.Tail1.addChild(this.Tail2);
		this.Torso.addChild(this.Tail1);
		this.RightBackLeg2.addChild(this.RightBackLeg3);
		this.Torso.addChild(this.Mane1);
		this.Head.addChild(this.RightEar1);
		this.Mane2.addChild(this.Neck);
		this.LeftBackLeg2.addChild(this.LeftBackLeg3);
		this.Mane1.addChild(this.Mane2);
		this.Neck.addChild(this.Head);
		this.RightEar1.addChild(this.RightEar2);
		this.Head.addChild(this.Snout);
		this.Head.addChild(this.LeftEar1);
		this.LeftEar1.addChild(this.LeftEar2);
		this.Torso.addChild(this.SaddleBase);
		this.SaddleBase.addChild(this.SaddleUpper);
		this.SaddleBase.addChild(this.SaddleLower);
		this.SaddleBase.addChild(this.SaddleStrap1);
		this.SaddleStrap1.addChild(this.SaddleMetal1);
		this.SaddleBase.addChild(this.SaddleStrap2);
		this.SaddleStrap2.addChild(this.SaddleMetal2);
	}

	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float scale) {

		super.render(entity, f1, f2, f3, f4, f5, scale);
		this.setRotationAngles(f1, f2, f3, f4, f5, scale, entity);

		EntityTerrakon entityTerrakon = (EntityTerrakon) entity;
		if (entityTerrakon.isChild()) {
			float f = 2.0F;
			GlStateManager.pushMatrix();
			GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
			GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
			this.Torso.render(scale);
			this.RightFrontLeg.render(scale);
			this.RightBackLeg1.render(scale);
			this.LeftFrontLeg.render(scale);
			this.LeftBackLeg1.render(scale);
			GlStateManager.popMatrix();
		} else {
			this.Torso.render(scale);
			this.RightFrontLeg.render(scale);
			this.RightBackLeg1.render(scale);
			this.LeftFrontLeg.render(scale);
			this.LeftBackLeg1.render(scale);
		}

		if (entityTerrakon.isSaddled()) {
			this.SaddleBase.isHidden = false;
			this.SaddleLower.isHidden = false;
			this.SaddleStrap1.isHidden = false;
			this.SaddleMetal1.isHidden = false;
			this.SaddleStrap2.isHidden = false;
			this.SaddleMetal2.isHidden = false;
			this.SaddleUpper.isHidden = false;
		} else {
			this.SaddleBase.isHidden = true;
			this.SaddleLower.isHidden = true;
			this.SaddleStrap1.isHidden = true;
			this.SaddleMetal1.isHidden = true;
			this.SaddleStrap2.isHidden = true;
			this.SaddleMetal2.isHidden = true;
			this.SaddleUpper.isHidden = true;
		}
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6, Entity entityIn) {
		super.setRotationAngles(f1, f2, f3, f4, f5, f6, entityIn);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float f1, float f2, float partialTickTime) {
		super.setLivingAnimations(entitylivingbaseIn, f1, f2, partialTickTime);
		EntityTerrakon terrakon = (EntityTerrakon) entitylivingbaseIn;

		if (terrakon.isSitting()) {

			this.Mane1.rotateAngleX = 0.045553093477052005F;
			this.Torso.rotateAngleX = -0.3642502148912167F;
			this.RightFrontLeg.rotateAngleX = 0;
			this.LeftFrontLeg.rotateAngleX = 0;
			this.RightBackLeg1.setRotationPoint(2.0F, 9.6F, 8.6F);
			this.RightBackLeg1.rotateAngleX = -0.45273840796732906F;
			this.RightBackLeg2.setRotationPoint(0.0F, 5.8F, -0.6F);
			this.RightBackLeg2.rotateAngleX = 1.3658946726107626F;
			this.RightBackLeg3.setRotationPoint(0.0F, 4.7F, 0.6000000000000001F);
			this.RightBackLeg3.rotateAngleX = -0.910538270765442F;
			this.LeftBackLeg1.setRotationPoint(-3.0F, 9.6F, 8.6F);
			this.LeftBackLeg1.rotateAngleX = -0.45273840796732906F;
			this.LeftBackLeg2.setRotationPoint(0.0F, 5.8F, -0.6F);
			this.LeftBackLeg2.rotateAngleX = 1.3658946726107626F;
			this.LeftBackLeg3.setRotationPoint(0.0F, 4.7F, 0.6F);
			this.LeftBackLeg3.rotateAngleX = -0.9105382707654417F;
			this.Tail1.rotateAngleX = 0F;
			this.Tail2.rotateAngleX = 0.13665928043115597F;
			this.Tail3.rotateAngleX = 0.09110618695410398F;

		} else {
			this.Mane1.rotateAngleX = -0.18203784098300857F;
			this.Torso.rotateAngleX = -0.16109388995907695F;
			this.RightBackLeg1.setRotationPoint(2.0F, 7.6F, 8.6F);
			this.RightBackLeg2.setRotationPoint(0.0F, 5.8F, -0.6F);
			this.RightBackLeg2.rotateAngleX = 0.9534733703645022F;
			this.RightBackLeg3.setRotationPoint(0.0F, 5.2F, 0.2F);
			this.RightBackLeg3.rotateAngleX = -0.48415433450322704F;
			this.LeftBackLeg1.setRotationPoint(-3.0F, 7.6F, 8.6F);
			this.LeftBackLeg2.setRotationPoint(0.0F, 5.8F, -0.6F);
			this.LeftBackLeg2.rotateAngleX = 0.9534733703645022F;
			this.LeftBackLeg3.setRotationPoint(0.0F, 5.2F, 0.2F);
			this.LeftBackLeg3.rotateAngleX = -0.48415433450322704F;
			this.Tail1.rotateAngleX = -0.09110618695410395F;
			this.Tail2.rotateAngleX = -0.0595157274930067F;
			this.Tail3.rotateAngleX = -0.08429940287132605F;

			this.LeftBackLeg1.rotateAngleX = -0.4527384079673295F + MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
			this.RightBackLeg1.rotateAngleX = -0.4527384079673295F + MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f2;
			this.RightFrontLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f2;
			this.LeftFrontLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
		}

		//this.Torso.rotateAngleX = -0.16109388995907695F + MathHelper.cos(f1 * 0.06f) * 2;
	}
}