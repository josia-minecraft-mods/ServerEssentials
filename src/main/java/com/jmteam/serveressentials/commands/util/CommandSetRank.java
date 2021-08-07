package com.jmteam.serveressentials.commands.util;

import com.jmteam.serveressentials.util.PlayerUtils;
import com.jmteam.serveressentials.util.RankUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class CommandSetRank extends CommandBase {
    @Override
    public String getName() {
        return "setrank";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + this.getName() + " <username> <rank_name>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {


        if (args.length > 1) {
            EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(args[0]);

            if (RankUtils.ranks.containsKey(args[1])) {
                PlayerUtils.players.get(player).setRankName(args[1]);
                PlayerUtils.savePlayerData(player, false);
                sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Succesfully Changed rank for " + args[0] + "!"));
                player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + "Your rank has been changed to: " + args[1] + "!"), false);

            } else {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "This rank doesn't exist!"));
            }
        } else {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }

        if(args.length == 2) {
            return getListOfStringsMatchingLastWord(args, RankUtils.getRankList());
        }

        return super.getTabCompletions(server, sender, args, targetPos);
    }

}
