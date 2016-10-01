package com.novaviper.tetracraft.common.entity;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Definition Class for the mod's {@link EntityModRideableTameable}
 */
public abstract class EntityModRideableTameable extends EntityModTameable implements IJumpingMount{

	private static final IAttribute JUMP_STRENGTH = (new RangedAttribute((IAttribute)null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D)).setDescription("Jump Strength").setShouldWatch(true);
	private static final DataParameter<Boolean> SADDLED = EntityDataManager.<Boolean>createKey(EntityModTameable.class, DataSerializers.BOOLEAN);
	/** Used to determine the sound that the horse should make when it steps */
	protected int gallopTime;
	public int sprintCounter;
	private boolean allowStandSliding;
	protected boolean horseJumping;
	protected float jumpPower;

	public EntityModRideableTameable(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(JUMP_STRENGTH);
		this.setSaddled(false);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(SADDLED, Boolean.valueOf(false)); // Saddle
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("Saddle", this.isSaddled());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		super.readEntityFromNBT(tagCompound);
		this.setSaddled(tagCompound.getBoolean("Saddle"));
	}

	public boolean isSaddled() {

		return ((Boolean)this.dataManager.get(SADDLED)).booleanValue();
	}

	public void setSaddled(boolean saddled) {
		if (saddled)
		{
			this.dataManager.set(SADDLED, Boolean.valueOf(true));
		}
		else
		{
			this.dataManager.set(SADDLED, Boolean.valueOf(false));
		}
	}

	public boolean isEntityJumping()
	{
		return this.horseJumping;
	}

	public void setEntityJumping(boolean jumping)
	{
		this.horseJumping = jumping;
	}

	public double getEntityJumpStength()
	{
		return this.getEntityAttribute(JUMP_STRENGTH).getAttributeValue();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setJumpPower(int jumpPowerIn)
	{
		if (this.isSaddled())
		{
			if (jumpPowerIn < 0)
			{
				jumpPowerIn = 0;
			}
			else
			{
				this.allowStandSliding = true;
			}

			if (jumpPowerIn >= 90)
			{
				this.jumpPower = 1.0F;
			}
			else
			{
				this.jumpPower = 0.4F + 0.4F * (float)jumpPowerIn / 90.0F;
			}
		}
	}

	@Override
	public boolean canJump()
	{
		return this.isSaddled();
	}

	@Override
	public void handleStartJump(int p_184775_1_)
	{
		this.allowStandSliding = true;
	}

	@Override
	public void handleStopJump() {}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.sprintCounter > 0)
		{
			++this.sprintCounter;

			if (this.sprintCounter > 300)
			{
				this.sprintCounter = 0;
			}
		}
	}

	protected abstract SoundEvent getStepSound();

	protected abstract SoundEvent getFallSound();

	protected abstract SoundEvent getGallopSound();

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		SoundType soundtype = blockIn.getSoundType();

		if (this.worldObj.getBlockState(pos.up()).getBlock() == Blocks.SNOW_LAYER)
		{
			soundtype = Blocks.SNOW_LAYER.getSoundType();
		}
		if (!blockIn.getDefaultState().getMaterial().isLiquid())
		{

			if (this.isBeingRidden())
			{
				++this.gallopTime;

				if (this.gallopTime > 5 && this.gallopTime % 3 == 0)
				{
					this.playSound(getGallopSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
				}
				else if (this.gallopTime <= 5)
				{
					this.playSound(getStepSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
				}
			}
		}
	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{
		if (distance > 1.0F)
		{
			this.playSound(this.getFallSound(), 0.4F, 1.0F);
		}

		int i = MathHelper.ceiling_float_int((distance * 0.5F - 3.0F) * damageMultiplier);

		if (i > 0)
		{
			this.attackEntityFrom(DamageSource.fall, (float)i);

			if (this.isBeingRidden())
			{
				for (Entity entity : this.getRecursivePassengers())
				{
					entity.attackEntityFrom(DamageSource.fall, (float)i);
				}
			}

			IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - (double)this.prevRotationYaw, this.posZ));
			Block block = iblockstate.getBlock();

			if (iblockstate.getMaterial() != Material.AIR && !this.isSilent())
			{
				SoundType soundtype = block.getSoundType();
				this.worldObj.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
			}
		}
	}

	/**
	 * Drop the equipment for this entity.
	 */
	@Override
	protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier)
	{
		super.dropEquipment(wasRecentlyHit, lootingModifier);

		if (this.isSaddled())
		{
			this.dropItem(Items.SADDLE, 1);
		}
	}

	public void makePlayerRide(EntityPlayer player){
		player.rotationYaw = this.rotationYaw;
		player.rotationPitch = this.rotationPitch;
		this.setSitting(false);

		if (isServer()){
			player.startRiding(this);
		}
	}

	/**
	 * Moves the entity based on the specified heading.
	 */
	@Override
	public void moveEntityWithHeading(float strafe, float forward)
	{
		if (this.isBeingRidden() && this.canBeSteered() && this.isSaddled())
		{
			EntityLivingBase entitylivingbase = (EntityLivingBase)this.getControllingPassenger();
			this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
			this.rotationPitch = entitylivingbase.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
			strafe = entitylivingbase.moveStrafing * 0.5F;
			forward = entitylivingbase.moveForward;

			if (forward <= 0.0F)
			{
				forward *= 0.25F;
				this.gallopTime = 0;
			}

			if (this.onGround && this.jumpPower == 0.0F && !this.allowStandSliding)
			{
				strafe = 0.0F;
				forward = 0.0F;
			}

			if (this.jumpPower > 0.0F && !this.isEntityJumping() && this.onGround)
			{
				this.motionY = this.getEntityJumpStength() * (double)this.jumpPower;

				if (this.isPotionActive(MobEffects.JUMP_BOOST))
				{
					this.motionY += (double)((float)(this.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
				}

				this.setEntityJumping(true);
				this.isAirBorne = true;

				if (forward > 0.0F)
				{
					float f = MathHelper.sin(this.rotationYaw * 0.017453292F);
					float f1 = MathHelper.cos(this.rotationYaw * 0.017453292F);
					this.motionX += (double)(-0.4F * f * this.jumpPower);
					this.motionZ += (double)(0.4F * f1 * this.jumpPower);
					this.playSound(SoundEvents.ENTITY_HORSE_JUMP, 0.4F, 1.0F);
				}

				this.jumpPower = 0.0F;
				net.minecraftforge.common.ForgeHooks.onLivingJump(this);
			}

			this.stepHeight = 1.0F;
			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

			if (this.canPassengerSteer())
			{
				this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				super.moveEntityWithHeading(strafe, forward);
			}
			else if (entitylivingbase instanceof EntityPlayer)
			{
				this.motionX = 0.0D;
				this.motionY = 0.0D;
				this.motionZ = 0.0D;
			}

			if (this.onGround)
			{
				this.jumpPower = 0.0F;
				this.setEntityJumping(false);
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d1 = this.posX - this.prevPosX;
			double d0 = this.posZ - this.prevPosZ;
			float f2 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

			if (f2 > 1.0F)
			{
				f2 = 1.0F;
			}

			this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		}
		else
		{
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.moveEntityWithHeading(strafe, forward);
		}
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	@Override
	public boolean canBePushed()
	{
		return !this.isBeingRidden();
	}

	/**
	 * Dead and sleeping entities cannot move
	 */
	@Override
	protected boolean isMovementBlocked()
	{
		return this.isBeingRidden() && this.isSaddled() ? true : super.isMovementBlocked();
	}

	/**
	 * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
	 * by a player and the player is holding a carrot-on-a-stick
	 */
	@Override
	public boolean canBeSteered()
	{
		Entity entity = this.getControllingPassenger();
		return entity instanceof EntityLivingBase;
	}

	/**
	 * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
	 * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
	 */
	@Override
	public Entity getControllingPassenger()
	{
		return this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
	}

	@Override
	public void updatePassenger(Entity passenger)
	{
		super.updatePassenger(passenger);

		if (passenger instanceof EntityLiving)
		{
			EntityLiving entityliving = (EntityLiving)passenger;
			this.renderYawOffset = entityliving.renderYawOffset;
		}
	}
}