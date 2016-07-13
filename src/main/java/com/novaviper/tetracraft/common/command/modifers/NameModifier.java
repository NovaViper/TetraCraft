package com.novaviper.tetracraft.common.command.modifers;

import com.novaviper.cryolib.common.helper.ChatHelper;
import com.novaviper.cryolib.lib.Strings;
import com.novaviper.tetracraft.common.command.CommandAdmin;
import com.novaviper.tetracraft.common.entity.EntityModTameable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/11/2016
 * @purpose Modifier Class to change the name of an {@link EntityModTameable} creature
 */
public class NameModifier implements CommandAdmin.EntityModifier {

	private final EntityPlayerMP player;
	private final String newName;

	public NameModifier(EntityPlayerMP player, String newName) {
		this.player = player;
		this.newName = newName;
	}

	@Override
	public void modify(EntityModTameable entity) {
		TextComponentTranslation text;
		if (entity.isTamed()) {
			if (entity.canInteract(player)) {
				if (!entity.getCustomNameTag().equals(newName)) {
					text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.success.name"), entity.checkName(entity), newName);
					text.getStyle().setColor(TextFormatting.GREEN);
					player.addChatMessage(text);
					entity.setCustomNameTag(newName);
				} else if (entity.getCustomNameTag().equals(newName)) {
					text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.already.name"), newName);
					text.getStyle().setColor(TextFormatting.RED);
					entity.alreadyModified(entity, player, text);
				}
			} else {
				entity.doNotOwnMessage(entity, player);
			}
		} else {
			text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.fail.name"));
			entity.cannotModify(entity, player, text);
		}
	}
}