package com.jmteam.serveressentiials.commands.util;

import com.jmteam.serveressentiials.util.RankUtils;
import com.jmteam.serveressentiials.util.WarpUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import scala.tools.reflect.quasiquotes.Rank;

import javax.annotation.Nullable;
import java.util.List;

public class CommandSetWarp extends CommandBase {
    @Override
    public String getName() {
        return "setwarp";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + this.getName() + " <warp_name>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            String warpName = args[0];
            EntityPlayerMP playerMP = (EntityPlayerMP) sender;

            WarpUtils.addWarp(warpName, sender.getPosition(), playerMP.world.provider.getDimension(), playerMP.rotationPitch, playerMP.rotationYaw);
            WarpUtils.saveWarps();
            sender.sendMessage(new TextComponentString("Added warp with name: " + warpName));
        } else {
            sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
        }
    }


}
