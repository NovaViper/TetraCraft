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
 * @purpose Modifier Class to change the tame state of an {@link EntityModTameable} creature
 */
public class TameModifier implements EntityModifier {

	private final EntityPlayerMP player;

	public TameModifier(EntityPlayerMP player) {
		this.player = player;
	}

	@Override
	public void modify(EntityModTameable entity) {
		TextComponentTranslation text;
		if(!entity.isTamed()) {
			entity.tamedFor(player, true);
			text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.success.tame"));
			text.getStyle().setColor(TextFormatting.GREEN);
			player.addChatMessage(text);
		}else {
			text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.already.tame"));
			text.getStyle().setColor(TextFormatting.RED);
			entity.alreadyModified(entity, player, text);
		}
	}
}