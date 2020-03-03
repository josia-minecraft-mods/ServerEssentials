package com.jmteam.serveressentiials.data;

import com.jmteam.serveressentiials.util.RankUtils;

import java.util.ArrayList;
import java.util.List;

public class RankData {

    private String prefix;
    private String inherit_rank;
    private List<String> permissions = new ArrayList<>();
    private transient List<String> inherit_permissions = new ArrayList<>();

    public void addPermissions(List<String> permissions) {
        inherit_permissions.addAll(permissions);
    }

    public List<String> getTotal_permissions() {
        return inherit_permissions;
    }

    public String getInherit_rank() {
        return inherit_rank;
    }

    public void setInherit_rank(String inherit_rank) {
        this.inherit_rank = inherit_rank;
    }

    public boolean canRunCommand(String command) {
        return hasPermission("command." + command);
    }

    public boolean hasPermission(String action) {

        if (permissions.contains("-" + action) || inherit_permissions.contains("-" + action)) {
            if (permissions.contains(action) || inherit_permissions.contains(action)) {
                return true;
            }
            return false;
        }

        if (permissions.contains("*") || inherit_permissions.contains("*")) return true;

        if (action.startsWith("command.")) {
            if (permissions.contains("command.*") || inherit_permissions.contains("command.*")) {
                return true;
            }
        }

        if (permissions.contains(action) || inherit_permissions.contains(action)) return true;


        return false;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void addPermission(String permission) {
        permissions.add(permission);
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setupInherit(String inherit_rank) {
        if (inherit_rank != null) {
            addPermissions(RankUtils.ranks.get(inherit_rank).permissions);
            if (RankUtils.ranks.get(inherit_rank).inherit_rank != null && !RankUtils.ranks.get(inherit_rank).inherit_rank.equalsIgnoreCase(inherit_rank)) {
                setupInherit(RankUtils.ranks.get(inherit_rank).inherit_rank);
            }
        }
    }
}
