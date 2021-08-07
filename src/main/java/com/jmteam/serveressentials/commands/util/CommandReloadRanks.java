package com.jmteam.serveressentials.commands.util;

import com.jmteam.serveressentials.util.PlayerUtils;
import com.jmteam.serveressentials.util.RankUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;


public class CommandReloadRanks extends CommandBase {

    @Override
    public String getName() {
        return "reload-ranks";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/ " + this.getName();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        RankUtils.loadRanks();
        PlayerUtils.checkPlayerRanks();
        sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Reloaded Ranks!"));
    }
}
