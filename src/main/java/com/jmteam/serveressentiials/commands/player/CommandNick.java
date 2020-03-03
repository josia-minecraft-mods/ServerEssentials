package com.jmteam.serveressentiials.commands.player;

import com.jmteam.serveressentiials.data.PlayerData;
import com.jmteam.serveressentiials.util.PlayerUtils;
import com.jmteam.serveressentiials.util.ServerUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandNick extends CommandBase {
    @Override
    public String getName() {
        return "nick";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + this.getName() + " <nickname>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayerMP) {
            if (args.length >= 1) {
                PlayerData data = PlayerUtils.getPlayerData((EntityPlayerMP) sender);
                data.setNickname(args[0]);
                PlayerUtils.savePlayerData(data);
                sender.sendMessage(new TextComponentString(ServerUtils.prefix + TextFormatting.GOLD + "Succesfully updated nickname!"));
            }
        }
    }
}
