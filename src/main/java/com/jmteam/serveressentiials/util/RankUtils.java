package com.jmteam.serveressentiials.util;

import com.google.common.reflect.TypeToken;
import com.jmteam.serveressentiials.ServerEssentials;
import com.jmteam.serveressentiials.data.PlayerData;
import com.jmteam.serveressentiials.data.RankData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import org.apache.logging.log4j.core.appender.rolling.action.IfAll;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankUtils {
    public static Map<String, RankData> ranks = new HashMap<>();
    public static Type ranktype = new TypeToken<Map<String, RankData>>() {
    }.getType();


    public static void addRank(String name, RankData data) {
        ranks.put(name, data);
        saveRanks();
    }

    public static List<String> getPermissions(String rankname) {
        return ranks.get(rankname).getPermissions();
    }

    public static void removeRank(String name) {
        ranks.remove(name);
    }

    public static void setPlayerRank(EntityPlayer player, String rank) {
        PlayerUtils.players.get(player).setRankName(rank);
        PlayerUtils.savePlayerData(PlayerUtils.players.get(player));
    }

    public static String ranksToJson(Map rankmap) {
        if (rankmap == null) rankmap = new HashMap();
        return ServerEssentials.JSON.toJson(rankmap);
    }

    public static boolean doesRankExist(String name) {
        return ranks.containsKey(name);
    }

    public static boolean doesRankForPlayerExist(EntityPlayer player) {
        PlayerData playerData = PlayerUtils.players.get(player);

        if(!doesRankExist(playerData.getRankName())) {
            playerData.setRankName("Default");
            PlayerUtils.savePlayerData(player, false);
        }

        return doesRankExist(playerData.getRankName());
    }

    public static Map jsonToRanks(String ranks) {
        Map rank = new HashMap();
        return (Map) (ranks != null && !ranks.isEmpty() ? (Map) ServerEssentials.JSON.fromJson(ranks, ranktype) : rank);
    }

    public static void loadRanks() {
        ranks.clear();
        Map<String, RankData> temp = new HashMap<>();

        String fileDir = (((File) FMLInjectionData.data()[6])).getAbsolutePath() + "/config/JMTeam/essentials/";
        String fileName = "ranks.json";
        File dir = new File(fileDir);
        File f = new File(fileDir + fileName);

        if(!f.exists()) {
            createDefaultRank();
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
                    temp = jsonToRanks(b.toString());
                }
            } catch (FileNotFoundException var8) {
                var8.printStackTrace();
            }
        }

        boolean shouldUpdate = false;

        // Prevent Dupe Ranks
        for(Map.Entry<String, RankData>  entry: temp.entrySet()) {
            if(!ranks.containsKey(entry.getKey())) {
                ranks.put(entry.getKey(), entry.getValue());
            }else{
                shouldUpdate = true;
            }
        }

        if(shouldUpdate) saveRanks();

        for (RankData rankData : ranks.values()) {
            if (rankData.getInherit_rank() != null) rankData.setupInherit(rankData.getInherit_rank());
        }
    }

    public static void saveRanks() {
        String fileDir = ((File) ((File) FMLInjectionData.data()[6])).getAbsolutePath() + "/config/JMTeam/essentials/";
        String fileName = "ranks.json";
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String json = ranksToJson(ranks);
        try {
            FileWriter fw = new FileWriter(dir + "/" + fileName);
            fw.write(json);
            fw.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }
    }

    public static void createDefaultRank() {
        if (RankUtils.ranks.isEmpty()) {
            // TODO make the default rank
            RankData data = new RankData();
            data.setPrefix("&0&l[&9Default&0&l]");
            //data.addPermission("*");
            RankUtils.ranks.put("Default", data);
            RankUtils.saveRanks();
        }
    }

    public static List<String> getRankList() {
        List<String> rank_list = new ArrayList<>();

        for (Map.Entry<String, RankData> entry : ranks.entrySet()) {
            rank_list.add(entry.getKey());
        }

        return rank_list;
    }
}
