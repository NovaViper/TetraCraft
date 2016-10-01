package com.novaviper.tetracraft.common.init;

import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import com.novaviper.cryolib.common.core.Logger;
import com.novaviper.cryolib.lib.Registers;
import com.novaviper.tetracraft.common.command.CommandAdmin;
import com.novaviper.tetracraft.common.command.CommandModHelp;
import com.novaviper.tetracraft.common.command.CommandTetraCraft;
import com.novaviper.tetracraft.common.command.CommandVersion;
import com.novaviper.tetracraft.common.command.interfaces.ISubCommandManager;
import com.novaviper.tetracraft.lib.ModReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Main class for defining and loading all commands
 */
public class ModCommands {

		static Logger log = Logger.createLogger(ModReference.MOD_NAME);

	 public static void load(FMLServerStartingEvent event){
		 Registers.addCommand(event, new CommandTetraCraft(event.getServer()));
	 }

	/**
	 * Register the sub-commands of the {@code /tetracraft} command.
	 *
	 * @param subCommand The sub-command manager of the command
	 */
	public static void registerSubCommands(ISubCommandManager subCommand) {
		addSubCommand(subCommand, new CommandModHelp(subCommand));
		addSubCommand(subCommand, new CommandVersion());
		addSubCommand(subCommand, new CommandAdmin());
	}

	/**
	 * Register the commands.
	 *
	 * @param subCommand The SubCommand Manager
	 * @param command The command to be registered
	 */
	private static void addSubCommand(ISubCommandManager subCommand, ICommand command) {
		Object name = command.getClass().getSimpleName();
		List<List<Object>> arrayList = new ArrayList<List<Object>>();
		List array = Arrays.asList(new Object[] {command});
		if(arrayList.contains(array))
			log.warn("Sub-Command: " +name+ " is already registered!");
		else {
			arrayList.add(array);
			log.info("Sub-Command: "+name+" has been registered");
			subCommand.registerSubCommand(command);
		}
	}
}