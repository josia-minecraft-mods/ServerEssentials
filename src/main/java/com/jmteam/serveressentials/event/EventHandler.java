package com.jmteam.serveressentials.event;

import com.jmteam.serveressentials.data.PlayerData;
import com.jmteam.serveressentials.data.RankData;
import com.jmteam.serveressentials.util.ChatUtil;
import com.jmteam.serveressentials.util.PlayerUtils;
import com.jmteam.serveressentials.util.RankUtils;
import com.jmteam.serveressentials.util.ReferencePermissions;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void JoinServer(PlayerEvent.PlayerLoggedInEvent e) {
        PlayerData data = PlayerUtils.loadPlayerData(e.player);

        PlayerUtils.players.put(e.player, data);
        RankUtils.doesRankForPlayerExist(e.player);
        PlayerUtils.opUser(((EntityPlayerMP) e.player).getServer(), e.player.getGameProfile());

        if (data.getRankName() == null) {
            RankUtils.setPlayerRank(e.player, "Default"); // TODO Config option for default ranking
        }
    }

    @SubscribeEvent
    public static void LeaveServer(PlayerEvent.PlayerLoggedOutEvent e) {
        PlayerUtils.players.remove(e.player);
        PlayerUtils.deopUser(e.player.getServer(), e.player.getGameProfile());
    }

    @SubscribeEvent
    public static void PlayerTick(TickEvent.PlayerTickEvent e) {
        GameProfile gameprofile = e.player.getServer().getPlayerList().getOppedPlayers().getGameProfileFromName(e.player.getName());
    }

    @SubscribeEvent
    public static void ChatEvent(ServerChatEvent e) {
        PlayerData data = PlayerUtils.players.get(e.getPlayer());
        RankData rank = data.getRank();

        boolean color = rank.hasPermission(ReferencePermissions.color_code);
        String rank_prefix = rank.getPrefix();
        String title = (data.getTitle() + (data.getTitle().isEmpty() ? "" : " "));
        String username = data.getNickname().isEmpty() ? e.getUsername() : data.getNickname();
        String message = e.getMessage().replaceAll("&", (color ? ChatUtil.makeColored() : "&"));

        e.setComponent(new TextComponentString(("<" + rank_prefix + " &r" + title + username + "&r> " ).replaceAll("&", ChatUtil.makeColored()) + message));
    }
}
