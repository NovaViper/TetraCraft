package com.novaviper.tetracraft.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.PlayerOffhandInvWrapper;
import net.minecraftforge.items.wrapper.RangedWrapper;
import com.novaviper.tetracraft.lib.ModReference;

import java.util.function.Predicate;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @author Choonster
 * @date 7/10/2016
 * @purpose Generic function class for the mod's bows
 */
public class ItemModBow extends ItemBow {
	public String bowType;

	public ItemModBow(String name,CreativeTabs tab, String type, int maxUsage) {
		bowType = type;
		ItemModGenericItem.setItemName(this, name);
		this.setCreativeTab(tab);
		this.setMaxDamage(maxUsage);

		// ItemBow's "pull" getter only works for Items.bow, so register a custom getter that works for any instance of this class.
		this.addPropertyOverride(new ResourceLocation(ModReference.MOD_ID, "pull"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn)
			{
				if (entityIn == null){
					return 0.0f;
				}

				ItemStack activeItemStack = entityIn.getActiveItemStack();
				if (activeItemStack != null && activeItemStack.getItem() instanceof ItemModBow) {
					return (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0f;
				}
				return 0.0f;
			}
		});
	}

	/**
	 * Get an {@link IItemHandler} wrapper of the first slot in the player's inventory containing an {@link ItemStack} of ammunition.
	 * <p>
	 * This {@link IItemHandler} will always have a single slot containing the ammunition {@link ItemStack}.
	 * <p>
	 *
	 * @param player The player
	 * @param isAmmo A function that detects whether a given ItemStack is valid ammunition
	 * @return The ammunition slot's IItemHandler, or null if there isn't any ammunition
	 */
	public static IItemHandler findAmmoSlot(EntityPlayer player, Predicate<ItemStack> isAmmo) {
		if (isAmmo.test(player.getHeldItemOffhand())) {
			return new PlayerOffhandInvWrapper(player.inventory);
		}

		// Vertical facing = main inventory
		final EnumFacing mainInventoryFacing = EnumFacing.UP;
		if (player.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, mainInventoryFacing)) {
			final IItemHandler mainInventory = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, mainInventoryFacing);

			if (isAmmo.test(player.getHeldItemMainhand())) {
				final int currentItem = player.inventory.currentItem;
				return new RangedWrapper((IItemHandlerModifiable) mainInventory, currentItem, currentItem + 1);
			}

			for (int slot = 0; slot < mainInventory.getSlots(); ++slot) {
				ItemStack itemStack = mainInventory.getStackInSlot(slot);

				if (isAmmo.test(itemStack)) {
					return new RangedWrapper((IItemHandlerModifiable) mainInventory, slot, slot + 1);
				}
			}
		}

		return null;
	}

	/**
	 * Is ammunition required to fire this bow?
	 *
	 * @param bow     The bow
	 * @param shooter The shooter
	 * @return Is ammunition required?
	 */
	protected boolean isAmmoRequired(ItemStack bow, EntityPlayer shooter) {
		return !shooter.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) == 0;
	}

	/**
	 * Nock an arrow.
	 *
	 * @param bow     The bow ItemStack
	 * @param shooter The player shooting the bow
	 * @param world   The World
	 * @param hand    The hand holding the bow
	 * @return The result
	 */
	protected ActionResult<ItemStack> nockArrow(ItemStack bow, World world, EntityPlayer shooter, EnumHand hand) {
		boolean hasAmmo = findAmmoSlot(shooter, this::isArrow) != null;

		ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(bow, world, shooter, hand, hasAmmo);
		if (ret != null) return ret;

		if (isAmmoRequired(bow, shooter) && !hasAmmo) {
			return new ActionResult<>(EnumActionResult.FAIL, bow);
		} else {
			shooter.setActiveHand(hand);
			return new ActionResult<>(EnumActionResult.SUCCESS, bow);
		}
	}

	/**
	 * Fire an arrow with the specified charge.
	 *
	 * @param bow     The bow ItemStack
	 * @param world   The firing player's World
	 * @param shooter The player firing the bow
	 * @param charge  The charge of the arrow
	 */
	protected void fireArrow(ItemStack bow, World world, EntityLivingBase shooter, int charge) {
		if (!(shooter instanceof EntityPlayer)) return;

		final EntityPlayer player = (EntityPlayer) shooter;
		final boolean ammoRequired = isAmmoRequired(bow, player);
		IItemHandler ammoSlot = findAmmoSlot(player, this::isArrow);

		charge = ForgeEventFactory.onArrowLoose(bow, world, player, charge, ammoSlot != null || !ammoRequired);
		if (charge < 0) return;

		if (ammoSlot != null || !ammoRequired) {
			if (ammoSlot == null) {
				ammoSlot = new ItemStackHandler(new ItemStack[]{new ItemStack(Items.ARROW)});
			}

			final ItemStack ammo = ammoSlot.getStackInSlot(0);

			final float arrowVelocity = getArrowVelocity(charge);

			if (arrowVelocity >= 0.1) {
				final boolean consumeAmmo = ammoRequired && ammo.getItem() instanceof ItemArrow;

				if (!world.isRemote) {
					ItemArrow itemArrow = (ItemArrow) (ammo.getItem() instanceof ItemArrow ? ammo.getItem() : Items.ARROW);
					EntityArrow entityArrow = itemArrow.createArrow(world, ammo, player);
					entityArrow.setAim(player, player.rotationPitch, player.rotationYaw, 0.0F, arrowVelocity * 3.0F, 1.0F);

					if (arrowVelocity == 1.0f) {
						entityArrow.setIsCritical(true);
					}

					int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bow);
					if (powerLevel > 0) {
						entityArrow.setDamage(entityArrow.getDamage() + (double) powerLevel * 0.5D + 0.5D);
					}

					int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bow);
					if (punchLevel > 0) {
						entityArrow.setKnockbackStrength(punchLevel);
					}

					if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow) > 0) {
						entityArrow.setFire(100);
					}

					bow.damageItem(1, player);

					if (!consumeAmmo) {
						entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
					}

					world.spawnEntityInWorld(entityArrow);
				}

				world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + arrowVelocity * 0.5F);

				if (consumeAmmo && ammoSlot.extractItem(0, 1, true) != null) {
					ammoSlot.extractItem(0, 1, false);
				}

				player.addStat(StatList.getObjectUseStats(this));
			}
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		int charge = this.getMaxItemUseDuration(stack) - timeLeft;
		fireArrow(stack, worldIn, entityLiving, charge);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		return nockArrow(itemStackIn, worldIn, playerIn, hand);
	}
}