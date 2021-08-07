package com.jmteam.serveressentials.util;

import com.google.gson.reflect.TypeToken;
import com.jmteam.serveressentials.ServerEssentials;
import com.jmteam.serveressentials.data.WarpData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class WarpUtils {

    public static Map<String, WarpData> warps = new HashMap<>();
    public static Type warpType = new TypeToken<Map<String, WarpData>>() {
    }.getType();

    public static void addWarp(String warpname, BlockPos pos, int dim, float pitch, float yaw) {
        warps.put(warpname, new WarpData(pos, pitch, yaw, dim));
    }

    public static void removeWarp(String warpname) {
        warps.remove(warpname);
    }

    public static void teleportToWarp(EntityPlayer player, String warpname) {
        WarpData data = warps.get(warpname);

        if (data != null) {
            BlockPos pos = data.getPosition();
            TeleportUtil.teleportToDimension(player, data.getDimension(), pos.getX(), pos.getY(), pos.getZ());
        } else {
            player.sendMessage(new TextComponentString(TextFormatting.RED + "This warp doesn't exist!"));
        }
    }

    public static String getWarpList() {
        StringBuilder builder = new StringBuilder();
        String out = "";
        int i = 0;

        if (WarpUtils.warps.size() > 0) {
            for (Map.Entry<String, WarpData> entry : warps.entrySet()) {
                i++;
                builder.append(entry.getKey() + (i == warps.size() ? "" : ", "));
            }
            out = "Listing " + i + " warps: " + builder.toString();
        } else {
            out = "There are no warps!";
        }

        return out;
    }

    public static void loadWarps() {
        warps.clear();
        String fileDir = (((File) FMLInjectionData.data()[6])).getAbsolutePath() + "/config/JMTeam/essentials/";
        String fileName = "warps.json";
        File dir = new File(fileDir);
        File f = new File(fileDir + fileName);

        if(!f.exists()) {
            return;
        }

        if (dir.exists() && f.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileDir + fileName));
                StringBuilder b = new StringBuilder();
                String line;
                try {
                    while ((line = br.readLine()) != null) {
                        b.append(line);
                    }
                } catch (IOException var7) {
                    var7.printStackTrace();
                }
                if (b.toString().length() > 0) {
                   warps = ServerEssentials.JSON.fromJson(b.toString(), warpType);
                }
            } catch (FileNotFoundException var8) {
                var8.printStackTrace();
            }
        }
    }

    public static void saveWarps() {
        IOUtils.writeToFile("config/JMTeam/essentials", "warps.json", warps);
    }
}
