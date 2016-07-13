package com.novaviper.tetracraft.common.command;

import com.google.common.collect.Lists;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import com.novaviper.cryolib.lib.Strings;
import com.novaviper.tetracraft.common.command.interfaces.ISubCommandManager;
import com.novaviper.tetracraft.common.init.ModCommands;
import com.novaviper.tetracraft.lib.ModReference;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @author Choonster
 * @date 7/10/2016
 * @purpose A command with sub-commands
 */
public class CommandTetraCraft extends CommandBase {

	/**
	 * The {@link ISubCommandManager} that manages the sub-commands of this command.
	 */
	private final SubCommandHandler subCommandHandler;

	public CommandTetraCraft(MinecraftServer server) {
		subCommandHandler = new SubCommandHandler(server);
	}

	/**
	 * Join the arguments array into a single string.
	 *
	 * @param args The arguments
	 * @return The joined string
	 */
	private static String joinArgs(String[] args) {
		return String.join(" ", (CharSequence[]) args);
	}

	/**
	 * Return a copy of the array with the first string removed.
	 * <p>
	 * Copied from {@link CommandHandler#dropFirstString}.
	 *
	 * @param input The original array
	 * @return The new array
	 */
	private static String[] dropFirstString(String[] input) {
		String[] output = new String[input.length - 1];
		System.arraycopy(input, 1, output, 0, input.length - 1);
		return output;
	}

	/**
	 * Gets the name of the command
	 */
	@Override
	public String getCommandName() {
		return "tetracraft";
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.<String>asList(new String[] {"tcraft", "tc", "TETRACRAFT", "TCRAFT", "TC"});
	}

	/**
	 * Gets the usage string for the command.
	 *
	 * @param sender The command sender
	 * @return The command usage
	 */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return Strings.getCommandUsage(ModReference.MOD_ID, "");
	}

	/**
	 * Callback for when the command is executed
	 *
	 * @param server The Minecraft server instance
	 * @param sender The source of the command invocation
	 * @param args   The arguments that were passed
	 */
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		subCommandHandler.executeCommand(sender, joinArgs(args));
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		return subCommandHandler.getTabCompletionOptions(sender, joinArgs(args), pos);
	}

	/**
	 * Return whether the specified command parameter index is a username parameter.
	 */
	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		final ICommand subCommand = subCommandHandler.getCommand(args[0]);
		return index > 0 && subCommand != null && subCommand.isUsernameIndex(dropFirstString(args), index - 1);
	}

	/**
	 * Handler for the sub-commands of this command.
	 */
	private static class SubCommandHandler extends CommandHandler implements ISubCommandManager {
		private final MinecraftServer server;
		/**
		 * A map of command names/aliases to commands in the order they were registered.
		 */
		private final Map<String, ICommand> orderedCommandMap = new LinkedHashMap<>();

		/**
		 * A set of the registered commands in the order they were registered.
		 */
		private final Set<ICommand> orderedCommandSet = new LinkedHashSet<>();

		private SubCommandHandler(MinecraftServer server) {
			this.server = server;
			ModCommands.registerSubCommands(this);
		}

		@Override
		protected MinecraftServer getServer() {
			return server;
		}

		@Override
		public ICommand registerCommand(ICommand command) {
			this.orderedCommandMap.put(command.getCommandName(), command);
			this.orderedCommandSet.add(command);

			for (String s : command.getCommandAliases())
			{
				ICommand icommand = (ICommand)this.orderedCommandMap.get(s);

				if (icommand == null || !icommand.getCommandName().equals(s))
				{
					this.orderedCommandMap.put(s, command);
				}
			}

			return super.registerCommand(command);
		}

		@Override
		public List<String> getTabCompletionOptions(ICommandSender sender, String input, BlockPos pos)
		{
			String[] astring = input.split(" ", -1);
			String s = astring[0];

			if (astring.length == 1)
			{
				List<String> list = Lists.<String>newArrayList();

				for (Map.Entry<String, ICommand> entry : this.orderedCommandMap.entrySet())
				{
					if (CommandBase.doesStringStartWith(s, (String)entry.getKey()) && ((ICommand)entry.getValue()).checkPermission(this.getServer(), sender))
					{
						list.add(entry.getKey());
					}
				}

				return list;
			}
			else
			{
				if (astring.length > 1)
				{
					ICommand icommand = (ICommand)this.orderedCommandMap.get(s);

					if (icommand != null && icommand.checkPermission(this.getServer(), sender))
					{
						return icommand.getTabCompletionOptions(this.getServer(), sender, dropFirstString(astring), pos);
					}
				}

				return Collections.<String>emptyList();
			}
		}

		public List<ICommand> getPossibleCommands(ICommandSender sender)
		{
			List<ICommand> list = Lists.<ICommand>newArrayList();

			for (ICommand icommand : this.orderedCommandSet)
			{
				if (icommand.checkPermission(this.getServer(), sender))
				{
					list.add(icommand);
				}
			}

			return list;
		}

		/**
		 * Register and return a sub-command.
		 *
		 * @param subCommand The sub-command
		 * @return The sub-command
		 */
		@Override
		public ICommand registerSubCommand(ICommand subCommand) {
			return registerCommand(subCommand);
		}

		/**
		 * Get the command with the specified name.
		 *
		 * @param commandName The command name
		 * @return The command, or null if there isn't one
		 */
		@Nullable
		public ICommand getCommand(String commandName) {
			return getCommands().get(commandName);
		}
	}
}