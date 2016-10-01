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
 * @purpose Modifier Class to change the age of an {@link EntityModTameable} creature
 */
public class AgeModifier implements EntityModifier {

	private final int age;
	private final EntityPlayerMP player;

	public AgeModifier(EntityPlayerMP player, int age) {
		this.age = age;
		this.player = player;
	}

	@Override
	public void modify(EntityModTameable entity) {
		TextComponentTranslation text;
		if (entity.isTamed()) {
			if (entity.canInteract(player)) {
				if (!entity.isChild() && age == -24000) { //Is an adult and the command paramter is baby
					entity.changeAge(true);
					text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.success.age.baby"), entity.checkName(entity));
					text.getStyle().setColor(TextFormatting.GREEN);
					player.addChatMessage(text);
				} else if (entity.isChild() && age == 1) { //Is a child and the command paramter is adult
					entity.changeAge(false);
					text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.success.age.adult"), entity.checkName(entity));
					text.getStyle().setColor(TextFormatting.GREEN);
					player.addChatMessage(text);
				} else if (entity.isChild() && age == -24000) { //Is an baby and the command paramter is baby
					text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.already.age.baby"));
					text.getStyle().setColor(TextFormatting.RED);
					entity.alreadyModified(entity, player, text);
				} else if (!entity.isChild() && age == 1) { //Is an adult and the command paramter is baby
					text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.already.age.adult"));
					text.getStyle().setColor(TextFormatting.RED);
					entity.alreadyModified(entity, player, text);
				}
			} else if (!entity.canInteract(player)) {
				entity.doNotOwnMessage(entity, player);
			}
		} else {
			text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.fail.age"));
			entity.cannotModify(entity, player, text);
		}
	}
}