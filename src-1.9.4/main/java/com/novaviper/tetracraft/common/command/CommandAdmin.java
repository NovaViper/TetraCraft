package com.novaviper.tetracraft.common.command;

import com.novaviper.tetracraft.common.command.modifers.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import com.novaviper.cryolib.lib.Strings;
import com.novaviper.tetracraft.common.entity.EntityModTameable;
import com.novaviper.tetracraft.lib.ModReference;

import java.util.Arrays;
import java.util.List;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Defines command for utilizing modifing subcommands
 */
public class CommandAdmin extends CommandBase {

	@Override
	public String getCommandName() {
		return "admin";
	}

	@Override
	public List getCommandAliases() {
		return Arrays.<String>asList(new String[]{"a", "*"});
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format(Strings.getCommandUsage(ModReference.MOD_ID, "admin"));
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 4;
	}

	private boolean getCommand(String paramIn, String name) {
		return paramIn.equalsIgnoreCase(name);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
		if (sender instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) sender;
			if (params.length < 1 || params[0].isEmpty()) {
				throw new WrongUsageException(getCommandUsage(sender));
			}

			// last parameter, optional
			boolean global = params[params.length - 1].equalsIgnoreCase("global") || params[params.length - 1].equalsIgnoreCase("g") || params[params.length - 1].equalsIgnoreCase("all") || params[params.length - 1].equalsIgnoreCase("a");
			String command = params[0];

			if (getCommand(command, "tame") || getCommand(command, "t")) {
				applyModifier(sender, new TameModifier(player), global, server);
			} else if (getCommand(command, "heal") || getCommand(command, "hp")) {
				applyModifier(sender, new HealthModifier(player), global, server);
			} else if (getCommand(command, "name") || getCommand(command, "n")) {
				if (params.length < 2) {
					throw new WrongUsageException(getCommandUsage(sender));
				}
				String parameter = params[1];

				if (!parameter.isEmpty()) {
					applyModifier(sender, new NameModifier(player, parameter), global, server);
				}
			} else if (command.equalsIgnoreCase("purge") || command.equalsIgnoreCase("p")) {
				if (params.length < 2) {
					throw new WrongUsageException(getCommandUsage(sender));
				}

				String parameter = params[1];

				if (parameter.equalsIgnoreCase("wild") || parameter.equalsIgnoreCase("w")) {
					applyModifier(sender, new PurgeModifier(player, false, "wild"), global, server);
				} else if (parameter.equalsIgnoreCase("tamed") || parameter.equalsIgnoreCase("t")) {
					applyModifier(sender, new PurgeModifier(player, false, "tamed"), global, server);
				} else if (parameter.equalsIgnoreCase("all") || parameter.equalsIgnoreCase("a")) {
					applyModifier(sender, new PurgeModifier(player, true, "all"), true, server);
				}
			} else if (getCommand(command, "age") || getCommand(command, "a")) {
				if (params.length < 2) {
					throw new WrongUsageException(getCommandUsage(sender));
				}

				String parameter = params[1];

				if (parameter.equalsIgnoreCase("baby") || parameter.equalsIgnoreCase("b")) {
					applyModifier(sender, new AgeModifier(player, -24000), global, server);
				} else if (parameter.equalsIgnoreCase("adult") || parameter.equalsIgnoreCase("a")) {
					applyModifier(sender, new AgeModifier(player, 1), global, server);
				}
			} else if (getCommand(command, "gender") || getCommand(command, "g")) {
				if (params.length < 2) {
					throw new WrongUsageException(getCommandUsage(sender));
				}

				String parameter = params[1];

				if (parameter.equalsIgnoreCase("male") || parameter.equalsIgnoreCase("boy")) {
					applyModifier(sender, new GenderModifier(player, true), global, server);
				} else if (parameter.equalsIgnoreCase("female") || parameter.equalsIgnoreCase("girl")) {
					applyModifier(sender, new GenderModifier(player, false), global, server);
				}
			} else {
				throw new CommandException(getCommandUsage(sender));
			}
		}
	}

	private boolean getArgs(String[] args, String name) {
		return args[0].equalsIgnoreCase(name);
	}

	/**
	 * Adds the strings available in this command to the given list of tab
	 * completion options.
	 */
	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) { //If the command name is filled, then list the sub commands
			return getListOfStringsMatchingLastWord(args, new String[]{"tame", "t", "heal", "hp", "name", "n", "purge", "p", "age", "a", "gender", "g"});
		} else if (args.length == 2) {//If the following sub-commands are filled, then fill their arguments
			if (args[0].equalsIgnoreCase("age") || args[0].equalsIgnoreCase("a")) {
				return getListOfStringsMatchingLastWord(args, new String[]{"baby", "adult"});
			} else if (args[0].equalsIgnoreCase("gender") || args[0].equalsIgnoreCase("g")) {
				return getListOfStringsMatchingLastWord(args, new String[]{"male", "boy", "female", "girl"});
			} else if (args[0].equalsIgnoreCase("purge") || args[0].equalsIgnoreCase("p")) {
				return getListOfStringsMatchingLastWord(args, new String[]{"wild", "w", "tamed", "t", "all", "a"});
			}
		}
		return null;
	}

	private void applyModifier(ICommandSender sender, EntityModifier modifier, boolean global, MinecraftServer server) throws CommandException {
		if (!global && sender instanceof EntityPlayerMP) {
			EntityPlayerMP player = getCommandSenderAsPlayer(sender);
			double range = 64;
			AxisAlignedBB aabb = new AxisAlignedBB(player.posX - 1, player.posY - 1, player.posZ - 1, player.posX + 1, player.posY + 1, player.posZ + 1);
			aabb = aabb.expand(range, range, range);
			List<Entity> entities = player.worldObj.getEntitiesWithinAABB(EntityModTameable.class, aabb);

			Entity closestEntity = null;
			float minPlayerDist = Float.MAX_VALUE;

			// get closest entity
			for (int i = 0; i < entities.size(); i++) {
				Entity entity = entities.get(i);
				float playerDist = entity.getDistanceToEntity(player);
				if (entity.getDistanceToEntity(player) < minPlayerDist) {
					closestEntity = entity;
					minPlayerDist = playerDist;
				}
			}

			if (closestEntity == null) {
				throw new CommandException(Strings.getCommandPhrase("notameable"));
			} else {
				modifier.modify((EntityModTameable) closestEntity);
			}
		} else {
			// scan all entities on all dimensions);
			for (WorldServer worldServer : server.worldServers) {
				List<Entity> entities = worldServer.loadedEntityList;

				for (int i = 0; i < entities.size(); i++) {
					Entity entity = entities.get(i);

					if (!(entity instanceof EntityModTameable)) {
						continue;
					}

					modifier.modify((EntityModTameable) entity);
				}
			}
		}
	}

	public interface EntityModifier {
		public void modify(EntityModTameable entity);
	}
}