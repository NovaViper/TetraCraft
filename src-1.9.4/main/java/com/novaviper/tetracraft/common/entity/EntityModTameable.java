package com.novaviper.tetracraft.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import com.novaviper.cryolib.common.helper.ChatHelper;
import com.novaviper.cryolib.lib.Constants;
import com.novaviper.cryolib.lib.Strings;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Definition Class for the mod's Tameable Entities
 */
public abstract class EntityModTameable extends EntityTameable {

	private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.<Integer>createKey(EntityModTameable.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> IS_BOY = EntityDataManager.<Boolean>createKey(EntityModTameable.class, DataSerializers.BOOLEAN);

	public EntityModTameable(World worldIn) {
		super(worldIn);
		this.updateEntityAttributes();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	}

	public void updateEntityAttributes() {}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(COLLAR_COLOR, new Integer((byte) EnumDyeColor.RED.getMetadata()));
		this.dataManager.register(IS_BOY, Boolean.valueOf(false)); // Gender

		boolean isGenderSet = false;

		if (isGenderSet == false) {
			if (rand.nextInt(2) == 0) {
				this.setGender(true);

			} else {
				this.setGender(false);
			}
			isGenderSet = true;
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("Angry", this.isAngry());
		tagCompound.setByte("CollarColor", (byte) this.getCollarColor().getDyeDamage());
		tagCompound.setBoolean("Gender", this.getGender());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		super.readEntityFromNBT(tagCompound);
		this.setAngry(tagCompound.getBoolean("Angry"));

		if (tagCompound.hasKey("CollarColor", 99)) {
			this.setCollarColor(EnumDyeColor.byDyeDamage(tagCompound.getByte("CollarColor")));
		}

		if (tagCompound.hasKey("Gender", 1)) {
			this.setGender(tagCompound.getBoolean("Gender"));
		}

		IAttributeInstance maxHealthInstance = this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
		IAttributeInstance damageInstance = this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);

		if (maxHealthInstance != null && damageInstance != null)
		{
			this.updateEntityAttributes();
		}
	}

		/* =======================UNIVERSAL======================= */

	/**
	 * Determines whether this wolf is angry or not.
	 */
	public boolean isAngry() {
		return (((Byte) this.dataManager.get(TAMED)).byteValue() & 2) != 0;
	}

	/**
	 * Sets whether this wolf is angry or not.
	 */
	public void setAngry(boolean angry) {
		byte b0 = ((Byte) this.dataManager.get(TAMED)).byteValue();

		if (angry) {
			this.dataManager.set(TAMED, Byte.valueOf((byte) (b0 | 2)));
		} else {
			this.dataManager.set(TAMED, Byte.valueOf((byte) (b0 & -3)));
		}
	}

	public EnumDyeColor getCollarColor() {
		return EnumDyeColor.byDyeDamage(((Integer) this.dataManager.get(COLLAR_COLOR)).intValue() & 15);
	}

	public void setCollarColor(EnumDyeColor collarcolor) {
		this.dataManager.set(COLLAR_COLOR, Integer.valueOf(collarcolor.getDyeDamage()));
	}

	/**
	 * Gets the gender of an entity.
	 *
	 * @return true = male, false = female
	 */
	public boolean getGender() {
		return ((Boolean) this.dataManager.get(IS_BOY)).booleanValue();
	}

	/**
	 * Sets the gender of an entity.
	 *
	 * @param gender
	 * @return true = male, false = female
	 */
	public void setGender(boolean gender) {
		if (gender) {
			this.dataManager.set(IS_BOY, Boolean.valueOf(true)); // Male
		} else {
			this.dataManager.set(IS_BOY, Boolean.valueOf(false)); // Female
		}
	}

	/**
	 * Gets the pitch of living sounds in living entities.
	 */
	protected float getPitch() {
		if (!this.isChild()) {
			return super.getSoundPitch() * -2;
		} else {
			return super.getSoundPitch() * 2;
		}
	}

	public void tamedFor(EntityPlayer player, boolean successful) { // NAV: For tame command and taming
		if (successful) {
			this.setTamed(true);
			this.navigator.clearPathEntity();
			this.setAttackTarget((EntityLivingBase) null);
			this.aiSit.setSitting(false);
			this.setOwnerId(player.getUniqueID());
			this.playTameEffect(true);
			this.worldObj.setEntityState(this, (byte) 7);
		} else {
			this.playTameEffect(false);
			this.worldObj.setEntityState(this, (byte) 6);
		}
	}

	public void unTame() {
		setTamed(false);
		navigator.clearPathEntity();
		setSitting(false);
		setOwnerId(null);
		if (this instanceof EntityModRideableTameable) {
			EntityModRideableTameable entity = (EntityModRideableTameable) this;
			if (entity.isSaddled()) {
				entity.setSaddled(false);
				entity.dropItem(Items.SADDLE, 1);
			}
		}
		this.worldObj.setEntityState(this, (byte) 6);
		this.updateEntityAttributes();
	}

	public Item[] populateEatableFoods() {
		return null;
	}

	public void changeAge(boolean isChild) {
		if (isChild) {
			this.setGrowingAge(-24000);
			if (this instanceof EntityModRideableTameable) {
				EntityModRideableTameable entity = (EntityModRideableTameable) this;
				if (entity.isSaddled()) {
					entity.setSaddled(false);
					entity.dropItem(Items.SADDLE, 1);
				}
			}
			this.updateEntityAttributes();
		} else {
			this.setGrowingAge(1);
			this.updateEntityAttributes();
		}
	}

	/**
	 * Get number of ticks, at least during which the living entity will be
	 * silent.
	 */
	@Override
	public int getTalkInterval() {
		if (getHealth() <= Constants.LOW_HP) {
			return 20;
		} else {
			return super.getTalkInterval();
		}
	}

	/**
	 * Sets the active target the Task system uses for tracking
	 */
	@Override
	public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
		super.setAttackTarget(entitylivingbaseIn);

		if (entitylivingbaseIn == null) {
			this.setAngry(false);
		} else if (!this.isTamed()) {
			this.setAngry(true);
		}
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (isServer() && this.getAttackTarget() == null && this.isAngry()) {
			this.setAngry(false);
		}
	}

	public String genderPronoun() {
		if (getGender() == true) {
			return "him";
		} else {
			return "her";
		}
	}

	public String genderSubject() {
		if (getGender() == true) {
			return "he";
		} else {
			return "she";
		}
	}

	public String checkName(EntityModTameable entity) {
		return !entity.getCustomNameTag().isEmpty() ? entity.getCustomNameTag() : entity.getName();
	}
	public void doNotOwnMessage(EntityModTameable entity, EntityPlayer player) {
		TextComponentTranslation text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("doesNotOwn.part1"), checkName(entity));
		text.getStyle().setColor(TextFormatting.RED);
		TextComponentTranslation text2 = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("doesNotOwn.part2"), entity.getOwner().getName());
		text2.getStyle().setColor(TextFormatting.RED);
		player.addChatMessage(text);
		player.addChatMessage(text2);
	}

	public void cannotModify(EntityModTameable entity, EntityPlayer player, ITextComponent interaction) {
		TextComponentTranslation text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("cannotModify"), interaction, checkName(entity));
		text.getStyle().setColor(TextFormatting.RED);
		player.addChatMessage(text);
	}

	public void alreadyModified(EntityModTameable entity, EntityPlayer player, ITextComponent interaction) {
		TextComponentTranslation text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("alreadyModified"), checkName(entity), interaction);
		text.getStyle().setColor(TextFormatting.RED);
		player.addChatMessage(text);
	}

	public void tooYoungForInteract(EntityModTameable entity, EntityPlayer player, ITextComponent interaction) {
		TextComponentTranslation text = ChatHelper.getChatComponentTranslation(Strings.getInteraction("entity.tooYoung"), checkName(entity), interaction);
		text.getStyle().setColor(TextFormatting.RED);
		player.addChatMessage(text);
	}

	@Override
	public float getEyeHeight() {
		return this.height;
	}

	@Override
	public boolean canBeLeashedTo(EntityPlayer player) {
		return !this.isAngry() && super.canBeLeashedTo(player);
	}

	public boolean canInteract(EntityPlayer player) {
		return this.getOwner() != null && this.isOwner(player) || this.getOwnerId() != null && this.getOwnerId().equals(player.getUniqueID());
	}

	/**
	 * Returns the entity's health relative to the maximum health.
	 *
	 * @return health normalized between 0 and 1
	 */
	public double getHealthRelative() {
		return getHealth() / (double) getMaxHealth();
	}

	/**
	 * Checks if this entity is running on a client.
	 * <p>
	 * Required since MCP's isClientWorld returns the exact opposite...
	 *
	 * @return true if the entity runs on a client or false if it runs on a
	 * server
	 */
	public boolean isClient() {
		return worldObj.isRemote;
	}

	/**
	 * Checks if this entity is running on a server.
	 *
	 * @return true if the entity runs on a server or false if it runs on a
	 * client
	 */
	public boolean isServer() {
		return !worldObj.isRemote;
	}
}