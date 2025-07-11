package com.thelow_accessory_hud.thelow_accessory_hud;

import java.io.File;

import com.thelow_accessory_hud.thelow_accessory_hud.commands.tah;
import com.thelow_accessory_hud.thelow_accessory_hud.config.ConfigHandler;
import com.thelow_accessory_hud.thelow_accessory_hud.hud.thelow_accessory_hudHUD;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "thelow_accessory_hud", version = "1.0")
public class thelow_accessory_hud {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File configFile = event.getSuggestedConfigurationFile();
        ConfigHandler.loadConfig(configFile);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        thelow_accessory_hudHUD.register();
        ClientCommandHandler.instance.registerCommand(new tah());
    }
}
