package com.jmteam.serveressentials.event;

import com.jmteam.serveressentials.util.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PermissionHandler {

    @SubscribeEvent
    public static void commandEvent(CommandEvent e) {
        if(e.getSender() instanceof EntityPlayerMP) {
            EntityPlayer player = (EntityPlayer) e.getSender();
            if(!PlayerUtils.players.get(player).getRank().canRunCommand(e.getCommand().getName())) {
                e.setCanceled(true);
                e.getSender().sendMessage(new TextComponentString(TextFormatting.RED + "You don't have permission!"));
            }
        }
    }
}
