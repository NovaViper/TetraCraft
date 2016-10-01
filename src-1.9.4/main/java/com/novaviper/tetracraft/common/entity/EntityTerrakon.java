package com.novaviper.tetracraft.common.entity;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import com.novaviper.cryolib.common.helper.ChatHelper;
import com.novaviper.tetracraft.common.init.ModItems;
import com.novaviper.tetracraft.common.init.ModLootTables;
import com.novaviper.tetracraft.common.util.ItemUtils;
import com.novaviper.cryolib.lib.Constants;
import com.novaviper.tetracraft.common.init.ModSoundEvents;

import java.util.UUID;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Main Entity class for the Terrakon
 */
public class EntityTerrakon extends EntityModRideableTameable {

	public EntityTerrakon(World worldIn) {
		super(worldIn);
		this.setSize(1f, 1.5f);
		this.setTamed(false);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate<Entity>()
		{
			public boolean apply(Entity entity)
			{
				return entity instanceof EntityCow || entity instanceof EntityRabbit;
			}
		}));
		//this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, false));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.updateEntityAttributes();
	}

	@Override
	public void updateEntityAttributes() {
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.40000001192092896D);
		if (this.isTamed()) {
			if (!this.isChild()) {
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
			} else {
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
			}

		} else {
			if (!this.isChild()) {
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
			} else {
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
			}
		}
	}

	@Override
	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_WOLF_STEP;
	}

	@Override
	protected SoundEvent getFallSound() {
		return SoundEvents.ENTITY_GENERIC_SMALL_FALL;
	}

	@Override
	protected SoundEvent getGallopSound() {
		return SoundEvents.ENTITY_HORSE_GALLOP;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return ModSoundEvents.terrakonHurt;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.terrakonDeath;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return isAngry() ? ModSoundEvents.terrakonGrowl : rand.nextInt(3) == 0
				? isTamed() && getHealth() <= Constants.LOW_HP ? ModSoundEvents.terrakonWhine
				: ModSoundEvents.terrakonPanting : ModSoundEvents.terrakonBark;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	public float getSoundVolume() {
		return 2F;
	}

	/**
	 * Gets the pitch of living sounds in living entities.
	 */
	@Override
	public float getPitch() {
		if (!isChild()) {
			return super.getSoundPitch();
		} else {
			return super.getSoundPitch() * 1;
		}
	}


	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
			Entity entity = source.getEntity();

			if (this.aiSit != null) {
				this.aiSit.setSitting(false);
			}

			if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
				amount = (amount + 1.0F) / 2.0F;
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

		if (flag) {
			this.applyEnchantments(this, entityIn);
			this.updateEntityAttributes();
		}

		return flag;
	}

	public void setTamed(boolean tamed) {
		super.setTamed(tamed);
		this.updateEntityAttributes();
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand, ItemStack stack) {
		if (this.isTamed()) {
			if (stack != null) {
				if (stack.getItem() instanceof ItemFood) {
					ItemFood itemfood = null;
					if (getHealthRelative() < 1) {
						itemfood = (ItemFood) ItemUtils.consumeEquipped(player, EntityEquipmentSlot.MAINHAND, this.populateEatableFoods());
						if (itemfood != null) {
							float volume = getSoundVolume() * 1.0f;
							float pitch = getPitch();
							BlockPos pos = this.getPosition();
							worldObj.playSound(player, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, volume, pitch);
							this.heal(itemfood.getHealAmount(stack));
						}
						return true;
					}
				} else if (ItemUtils.hasEquipped(player, EntityEquipmentSlot.MAINHAND, Items.SHEARS) && this.canInteract(player)) {
					if (isServer()) {
						unTame();
						this.setSaddled(false);
					}
					return true;
				}
				else if (ItemUtils.hasEquipped(player, EntityEquipmentSlot.MAINHAND, Items.SADDLE) && !this.isSaddled() && isServer()) {
					if(this.canInteract(player)) {
						if (!this.isChild()) {
							this.setSaddled(true);
							this.playSound(ModSoundEvents.Saddle, 0.5F, 1.0F);
							ItemUtils.consumeEquipped(player, EntityEquipmentSlot.MAINHAND, Items.SADDLE);
						} else {
							TextComponentTranslation text = ChatHelper.getChatComponentTranslation("interaction.tetracraft.entity.cannotBeRidden");
							this.tooYoungForInteract(this, player, text);
							return true;
						}
					}else{
						this.doNotOwnMessage(this, player);
					}
				}
				else if (ItemUtils.consumeEquipped(player, EntityEquipmentSlot.MAINHAND, Items.DYE) && this.canInteract(player)) {
					EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
					if (enumdyecolor != this.getCollarColor()) {
						this.setCollarColor(enumdyecolor);
						return true;
					}
				}
				/*else if (stack.getItem() == Items.stick && canInteract(player))
				{
					if (isServer()) {
						player.openGui(TetraCraft.instance, CommonProxy.PetPack, this.worldObj, this.getEntityId(), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
						this.worldObj.playSoundEffect(this.posX, this.posY + 0.5D, this.posZ, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
						return true;
					}
				}*/
			}
			if (canInteract(player) && isServer() && !this.isBreedingItem(stack)) {
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.navigator.clearPathEntity();
				this.setAttackTarget((EntityLivingBase)null);
			}

			if(this.isSaddled() && !player.onGround){
				if(this.canInteract(player)) {
					this.makePlayerRide(player);
				}else{
					this.doNotOwnMessage(this, player);
				}
				return true;
			}
		} else if (ItemUtils.consumeEquipped(player, EntityEquipmentSlot.MAINHAND, Items.BONE) && !this.isAngry()) {
			if (isServer()) {
				tamedFor(player, this.rand.nextInt(3) == 0);
			}
			return true;
		}
		return super.processInteract(player, hand, stack);
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
	 * the animal type)
	 */
	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return (stack == null ? false : stack.getItem() == ModItems.breedingBone);
	}

	@Override
	public Item[] populateEatableFoods() {
		super.populateEatableFoods();
		Item[] food = {Items.BEEF, Items.CHICKEN, Items.PORKCHOP, Items.MUTTON, Items.RABBIT, Items.FISH,
				Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_PORKCHOP, Items.COOKED_MUTTON, Items.COOKED_RABBIT, Items.COOKED_FISH};

		return food;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return ModLootTables.LootTableTerrakon;
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		if (otherAnimal == this) {
			return false;
		} else if (!this.isTamed()) {
			return false;
		} else if (!(otherAnimal instanceof EntityTerrakon)) {
			return false;
		} else {
			EntityTerrakon entityTerrakon = (EntityTerrakon) otherAnimal;
			return !entityTerrakon.isTamed() ? false : (entityTerrakon.isSitting() ? false
					: this.getGender() == entityTerrakon.getGender() ? false
					: this.isInLove() && entityTerrakon.isInLove());
		}
	}

	@Override
	public EntityTerrakon createChild(EntityAgeable ageable) {
		EntityTerrakon entityTerrakon = new EntityTerrakon(this.worldObj);
		UUID uuid = this.getOwnerId();

		if (uuid != null) {
			entityTerrakon.setOwnerId(uuid);
			entityTerrakon.setTamed(true);
		}

		return entityTerrakon;
	}
}