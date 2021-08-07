package com.jmteam.serveressentials.util;

import com.jmteam.serveressentials.data.PlayerData;
import com.jmteam.serveressentials.data.RankData;
import net.minecraft.entity.player.EntityPlayerMP;

public class PermissionUtil {

    public static boolean canRunCommand(EntityPlayerMP playerMP, String command) {
        return hasPermission(playerMP, "command." + command);
    }

    public static boolean hasPermission(EntityPlayerMP playerMP, String execute) {
        PlayerData data = PlayerUtils.getPlayerData(playerMP);
        RankData rank = data.getRank();

        if(rank.getPermissions().contains("-" + execute)) {
            if(rank.getPermissions().contains(execute)) {
                return true;
            }

            return false;
        }

        if(rank.getPermissions().contains("*")) {
            return true;
        }

        if(rank.getPermissions().contains(execute)) {
            return true;
        }

        return false;
    }
}
