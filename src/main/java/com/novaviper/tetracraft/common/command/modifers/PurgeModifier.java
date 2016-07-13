package com.novaviper.tetracraft.common.command.modifers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import com.novaviper.cryolib.lib.Strings;
import com.novaviper.tetracraft.common.command.CommandAdmin;
import com.novaviper.tetracraft.common.command.CommandAdmin.EntityModifier;
import com.novaviper.tetracraft.common.entity.EntityModTameable;
import com.novaviper.cryolib.common.helper.ChatHelper;
import com.novaviper.tetracraft.lib.ModReference;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Modifier Class to kill one or all {@link EntityModTameable} creatures in the world
 */
public class PurgeModifier implements EntityModifier {

	private final boolean killAll;
	private final String killWhat;
	private final EntityPlayerMP player;

	public PurgeModifier(EntityPlayerMP player, boolean kill, String type) {
		this.killAll = kill;
		this.killWhat = type;
		this.player = player;
	}

	@Override
	public void modify(EntityModTameable entity) {
		TextComponentTranslation text;
		if (killAll == false) {
			if (killWhat == "wild") {
				if (!entity.isTamed()) {
					entity.setDead();
					text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.success.purge.wild"));
					text.getStyle().setColor(TextFormatting.GREEN);
					player.addChatMessage(text);
				}
			}
			else if (killWhat == "tamed") {
				if (entity.isTamed()) {
					entity.setDead();
					text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.success.purge.tamed"));
					text.getStyle().setColor(TextFormatting.GREEN);
					player.addChatMessage(text);
				}
			}
		}
		else if (killWhat == "all") {
			entity.setDead();
			text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.success.purge.all"));
			text.getStyle().setColor(TextFormatting.GREEN);
			player.addChatMessage(text);
		}
	}
}