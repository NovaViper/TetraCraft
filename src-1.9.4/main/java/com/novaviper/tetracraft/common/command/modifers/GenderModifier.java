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
 * @purpose Modifier Class to change the gender of an {@link EntityModTameable} creature
 */
public class GenderModifier implements EntityModifier {

	private final EntityPlayerMP player;
	private final boolean gender;

	public GenderModifier(EntityPlayerMP player, boolean gender) {
		this.player = player;
		this.gender = gender;
	}

	@Override
	//@formatter:off
		public void modify(EntityModTameable entity) {
		TextComponentTranslation text;
			 if (entity.isTamed()) {
				if(entity.canInteract(player)) {
					if (entity.getGender() != gender) {
						entity.setGender(gender);
						if(gender == true) {
							text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.success.gender.male"), entity.checkName(entity));
							text.getStyle().setColor(TextFormatting.GREEN);
							player.addChatMessage(text);
						}else{
							text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.success.gender.female"), entity.checkName(entity));
							text.getStyle().setColor(TextFormatting.GREEN);
							player.addChatMessage(text);
						}
					} else if (entity.getGender() == gender && gender == true) {
						text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.already.gender.male"));
						text.getStyle().setColor(TextFormatting.RED);
						entity.alreadyModified(entity, player, text);
					}else if (entity.getGender() == gender && gender == false){
						text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.already.gender.female"));
						text.getStyle().setColor(TextFormatting.RED);
						entity.alreadyModified(entity, player, text);
					}
				}
				else if (!entity.canInteract(player)){
					entity.doNotOwnMessage(entity, player);
			}
			else {
				text = ChatHelper.getChatComponentTranslation(Strings.getCommandPhrase("interact.fail.gender"));
				entity.cannotModify(entity, player, text);
			}
		}
	}
}