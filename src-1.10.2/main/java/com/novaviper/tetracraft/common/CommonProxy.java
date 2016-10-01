package com.novaviper.tetracraft.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Loads stuff on the server side
 */
public class CommonProxy implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		/*if (ID == IDs.nileTableGUI) {
			TileEntity target = world.getTileEntity(new BlockPos(x, y, z));
			if (!(target instanceof TileEntityNileWorkbench)) {
				return null;
			}

			TileEntityNileWorkbench tileNileTable = (TileEntityNileWorkbench) target;
			ContainerNileWorkbench tableContainer = new ContainerNileWorkbench(tileNileTable, player.inventory, world, new BlockPos(x, y, z));
			return tableContainer;
		}*/
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		/*if (ID == IDs.nileTableGUI) {
			TileEntity target = world.getTileEntity(new BlockPos(x, y, z));
			if (!(target instanceof TileEntityNileWorkbench)) {
				return null;
			}

			TileEntityNileWorkbench tileNileTable = (TileEntityNileWorkbench) target;
			GuiNileWorkbench tableGui = new GuiNileWorkbench(player.inventory, tileNileTable, world, new BlockPos(x, y, z));
			return tableGui;
		}*/
		return null;
	}

	// Client Objects\\
	public void onPreInit(FMLPreInitializationEvent event){}

	public void onInit(FMLInitializationEvent event){}

	public void onPostInit(FMLPostInitializationEvent event){}

	public void onServerStarting(FMLServerStartingEvent event){}

	public void onServerStarted(FMLServerStartedEvent event){}

	public void onServerStopping(FMLServerStoppingEvent event){}

	public void onServerStopped(FMLServerStoppedEvent event){}

	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}

	public EntityPlayer getPlayerEntity() {
		return null;
	}

	//Particles
	//public void spawnName(World world, Entity entity) {}
}
