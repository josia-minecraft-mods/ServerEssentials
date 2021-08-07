package com.jmteam.serveressentials.commands.staff;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandFly extends CommandBase {
    @Override
    public String getName() {
        return "fly";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + this.getName();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if ((sender instanceof EntityPlayerMP)) {
            EntityPlayerMP p = (EntityPlayerMP) sender;
            p.capabilities.allowFlying = !p.capabilities.allowFlying;
            p.capabilities.isFlying = !p.capabilities.isFlying;
            p.sendPlayerAbilities();
            sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "You can" + (p.capabilities.allowFlying ? " now fly!" : " no longer fly!")));
        }
    }
}
