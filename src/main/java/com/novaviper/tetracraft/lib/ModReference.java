package com.novaviper.tetracraft.lib;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Define and List the mod's information and organize it
 */
//@formatter:off
public class ModReference {

    //Mod Details\\
    public static final String MOD_ID = "tetracraft";
    public static final String CHANNEL = "TETRACRAFT";
    public static final String MOD_NAME = "TETRACRAFT";
    public static final String MOD_VERSION = "0.1.0";
    public static final String MC_VERSION = "[1.9.4]";
    public static final String UPDATE_JSON = "https://raw.githubusercontent.com/NovaViper/TetraCraft/master/versions.json";
    public static final String DEPENDENCIES = "required-after:cryolib@[0.1.0]";

    //Mod Elements\\
    public static final String CLIENT_PROXY = "com.novaviper.tetracraft.client.ClientProxy";
    public static final String SERVER_PROXY = "com.novaviper.tetracraft.common.CommonProxy";
    public static final String GUI_FACTORY = "com.novaviper.tetracraft.client.gui.config.ConfigGuiFactory";

}