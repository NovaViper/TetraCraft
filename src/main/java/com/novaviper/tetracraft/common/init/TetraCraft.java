package com.novaviper.tetracraft.common.init;

import com.novaviper.cryolib.common.core.Logger;
import com.novaviper.cryolib.lib.Registers;
import com.novaviper.tetracraft.common.handler.ConfigHandler;
import com.novaviper.tetracraft.common.world.gen.ModOreDictionary;
import com.novaviper.tetracraft.common.world.gen.ModOreGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import com.novaviper.tetracraft.common.CommonProxy;
import com.novaviper.tetracraft.common.packets.PacketHandler;
import com.novaviper.tetracraft.lib.ModReference;

import java.io.File;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Main loading class for the mod
 */
@Mod(modid = ModReference.MOD_ID, name = ModReference.MOD_NAME, version = ModReference.MOD_VERSION, updateJSON = ModReference.UPDATE_JSON, acceptedMinecraftVersions = ModReference.MC_VERSION, dependencies = ModReference.DEPENDENCIES, guiFactory = ModReference.GUI_FACTORY)
public class TetraCraft {
	@Instance(value = ModReference.MOD_ID)
	public static TetraCraft instance;

	@SidedProxy(clientSide = ModReference.CLIENT_PROXY, serverSide = ModReference.SERVER_PROXY)
	public static CommonProxy proxy;
	static Logger log = Logger.createLogger(ModReference.MOD_NAME);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		log.info("**********PRE-INITIALIZATION LOAD STARTING**********");
		ConfigHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + ModReference.MOD_NAME + File.separator + ModReference.MOD_ID + ".cfg"));
		ModCreativeTabs.load();
		ModItems.load();
		ModBlocks.load();
		ModRecipes.load();
		ModEntities.load();
		ModEntities.loadProperties();
		ModSoundEvents.registerSounds();
		proxy.onPreInit(event);
		log.info("**********PRE-INITIALIZATION LOAD COMPLETED**********");
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		log.info("**********INITIALIZATION LOAD STARTING**********");
		proxy.onInit(event);
		PacketHandler.registerPackets();
		ModOreDictionary.loadOre();
		Registers.addWorldGenerator(new ModOreGenerator(), 0);
		log.info("**********INITIALIZATION LOAD COMPLETED**********");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		log.info("**********POST INITIALIZATION LOAD STARTING**********");
		proxy.onPostInit(event);
		log.info("**********POST INITIALIZATION LOAD COMPLETED**********");
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		log.info("**********SERVER INITIALIZATION LOAD STARTING**********");
		ModCommands.load(event);
		proxy.onServerStarting(event);
		log.info("**********SERVER INITIALIZATION LOAD COMPLETED**********");
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event) {
		log.info("**********SERVER INITIALIZED LOAD STARTING**********");
		proxy.onServerStarted(event);
		log.info("**********SERVER INITIALIZED LOAD COMPLETED**********");
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		log.info("**********SERVER STOPPING LOAD STARTING**********");
		proxy.onServerStopping(event);
		log.info("**********SERVER STOPPING LOAD COMPLETED**********");
	}

	@EventHandler
	public void serverStop(FMLServerStoppedEvent event) {
		log.info("**********SERVER STOPPED LOAD STARTING**********");
		proxy.onServerStopped(event);
		log.info("**********SERVER STOPPED LOAD COMPLETED**********");
	}
}