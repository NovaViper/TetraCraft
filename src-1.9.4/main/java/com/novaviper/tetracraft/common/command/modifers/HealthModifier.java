package com.novaviper.tetracraft.common.command.modifers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import com.novaviper.cryolib.lib.Strings;
import com.novaviper.tetracraft.common.command.CommandAdmin.EntityModifier;
import com.novaviper.tetracraft.common.entity.EntityModTameable;
import com.novaviper.cryolib.common.helper.ChatHelper;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Modifier Class to heal an {@link EntityModTameable} creature
 */
public class HealthModifier implements EntityModifier {

	private final EntityPlayerMP player;

	public HealthModifier(EntityPlayerMP player) {
		this.player = player;
	}

	@Override
	public void modify(EntityModTameable entity) {
		TextComponentTranslation text;
		if (((EntityModTameable)entity).isTamed()) {
			if (((EntityModTameable)entity).getHealthRelative() < 1) {
				if (((EntityModTameable)entity).canInteract(player)) {
					((EntityModTameable)entity).setHealth(((EntityModTameable)entity).getMaxHealth());
					text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.success.heal"), ((EntityModTameable)entity).checkName((EntityModTameable)entity));
					text.getStyle().setColor(TextFormatting.GREEN);
					player.addChatMessage(text);
				} else {
					((EntityModTameable)entity).doNotOwnMessage((EntityModTameable) entity, player);
				}
			} else {
				text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.already.heal"));
				text.getStyle().setColor(TextFormatting.RED);
				((EntityModTameable)entity).alreadyModified((EntityModTameable)entity, player, text);
			}
		} else {
			text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.fail.heal"));
			text.getStyle().setColor(TextFormatting.RED);
			((EntityModTameable)entity).cannotModify((EntityModTameable)entity, player, text);
		}
	}
}