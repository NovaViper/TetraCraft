package com.novaviper.tetracraft.common.command.interfaces;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandManager;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @author Choonster
 * @date 7/10/2016
 * @purpose A manager for sub-commands of an {@link ICommand}.
 */
public interface ISubCommandManager extends ICommandManager {
	/**
	 * Register and return a sub-command.
	 *
	 * @param subCommand The sub-command
	 * @return The sub-command
	 */
	ICommand registerSubCommand(ICommand subCommand);
}
