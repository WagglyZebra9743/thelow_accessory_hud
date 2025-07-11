package com.thelow_accessory_hud.thelow_accessory_hud.commands;

import com.thelow_accessory_hud.thelow_accessory_hud.config.ConfigHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class tah extends CommandBase {

    public static void register(FMLServerStartingEvent event) {
        event.registerServerCommand(new tah());
    }

    @Override
    public String getCommandName() {
        return "tah";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/tah <x> <y> - HUD表示位置を変更します";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.addChatMessage(new ChatComponentText("§c使用方法: /tah <x> <y>"));
            return;
        }

        try {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);

            ConfigHandler.hudX = x;
            ConfigHandler.hudY = y;
            ConfigHandler.save();

            sender.addChatMessage(new ChatComponentText("§a[thelow_accessory_hud]表示位置を §e(" + x + ", " + y + ") §aに変更しました。"));
        } catch (NumberFormatException e) {
            sender.addChatMessage(new ChatComponentText("§c[thelow_accessory_hud]数値が無効です。整数で指定してください。"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0; // 誰でも使用可能
    }
}
