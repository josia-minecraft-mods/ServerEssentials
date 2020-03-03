package com.jmteam.serveressentiials.data;

import com.jmteam.serveressentiials.util.RankUtils;

public class PlayerData {


    private String uuid;
    private int currency;
    private String rank = "";

    private String nickname = "";
    private String title = "";

    public int getCurrency() {
        return currency;
    }

    public String getRankName() {
        return rank;
    }

    public String getUuid() {
        return uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNickname(String username) {
        this.nickname = username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public RankData getRank() {
       return RankUtils.ranks.get(getRankName());
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public void setRankName(String rank) {
        this.rank = rank;
    }
}
