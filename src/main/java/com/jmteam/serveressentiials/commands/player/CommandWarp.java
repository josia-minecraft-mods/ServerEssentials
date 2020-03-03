package com.jmteam.serveressentiials.commands.player;

import com.jmteam.serveressentiials.util.WarpUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandWarp extends CommandBase {

    @Override
    public String getName() {
        return "warp";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + this.getName() + " <warp_name>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if(args.length == 1) {
            String warpName = args[0];

            WarpUtils.teleportToWarp((EntityPlayer) sender, warpName);
        }else{
            sender.sendMessage(new TextComponentString(WarpUtils.getWarpList()));
        }
    }
}
