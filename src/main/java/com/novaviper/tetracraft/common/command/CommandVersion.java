package com.novaviper.tetracraft.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import com.novaviper.cryolib.common.helper.ChatHelper;
import com.novaviper.cryolib.lib.Strings;
import com.novaviper.tetracraft.lib.ModReference;

import java.util.Arrays;
import java.util.List;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose A command that pulls up the mod's information
 */
public class CommandVersion extends CommandBase{


	@Override
	public String getCommandName() {
		return "version";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return Strings.getCommandUsage(ModReference.MOD_ID, "version");
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.<String>asList(new String[] {"v"});
	}

	private TextFormatting checkVersionStatus(){
		ForgeVersion.Status getStatus = ForgeVersion.getResult(FMLCommonHandler.instance().findContainerFor(ModReference.MOD_ID)).status;
		if(getStatus == ForgeVersion.Status.BETA_OUTDATED){
			return TextFormatting.RED;
		}else if(getStatus == ForgeVersion.Status.OUTDATED){
			return TextFormatting.RED;
		}else if(getStatus == ForgeVersion.Status.BETA){
			return TextFormatting.GREEN;
		}else if(getStatus == ForgeVersion.Status.AHEAD){
			return TextFormatting.DARK_PURPLE;
		}else if(getStatus == ForgeVersion.Status.UP_TO_DATE){
			return TextFormatting.AQUA;
		}else{
			return TextFormatting.GRAY;
		}
	}

	private String versionString(){
		ForgeVersion.Status getStatus = ForgeVersion.getResult(FMLCommonHandler.instance().findContainerFor(ModReference.MOD_ID)).status;
		if(getStatus == ForgeVersion.Status.BETA_OUTDATED){
			return "Beta (Outdated)";
		}else if(getStatus == ForgeVersion.Status.OUTDATED){
			return "Outdated";
		}else if(getStatus == ForgeVersion.Status.BETA){
			return "Beta";
		}else if(getStatus == ForgeVersion.Status.AHEAD){
			return "Ahead";
		}else if(getStatus == ForgeVersion.Status.UP_TO_DATE){
			return "Update to Date";
		}else{
			return "Unknown";
		}
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		this.buildTable(sender);
	}

	public void buildTable(ICommandSender sender){
		TextComponentTranslation separator = ChatHelper.getChatComponentTranslation(Strings.commandSepartor);
		TextComponentTranslation text1 = ChatHelper.getChatComponentTranslation(ModReference.MOD_NAME + " - " + TextFormatting.GREEN + ModReference.MOD_VERSION);
		TextComponentTranslation text2 = ChatHelper.getChatComponentTranslation("Build Status: " + checkVersionStatus() + versionString());
		TextComponentTranslation text3 = ChatHelper.getChatComponentTranslation("Minecraft Version: " + TextFormatting.GRAY + MinecraftForge.MC_VERSION);
		TextComponentTranslation text4 = ChatHelper.getChatComponentTranslation("Minecraft Forge Version: " + TextFormatting.LIGHT_PURPLE + ForgeVersion.getVersion());
		separator.getStyle().setColor(TextFormatting.GOLD);
		sender.addChatMessage(separator);
		sender.addChatMessage(text1);
		sender.addChatMessage(text2);
		sender.addChatMessage(text3);
		sender.addChatMessage(text4);
	}
}