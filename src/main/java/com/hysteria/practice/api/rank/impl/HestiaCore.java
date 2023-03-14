package com.hysteria.practice.api.rank.impl;

import me.quartz.hestia.HestiaAPI;
import com.hysteria.practice.api.rank.Rank;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HestiaCore implements Rank {

    @Override
    public String getName(UUID uuid) {
        return HestiaAPI.instance.getRank(uuid);
    }

    @Override
    public String getPrefix(UUID uuid) {
        return HestiaAPI.instance.getRankPrefix(uuid);
    }

    @Override
    public String getSuffix(UUID uuid) {
        return HestiaAPI.instance.getRankSuffix(uuid);
    }

    @Override
    public String getColor(UUID uuid) {
        return HestiaAPI.instance.getRank(uuid);
    }

    @Override
    public String getRealName(Player player) {
        return null;
    }

    @Override
    public String getTag(Player player) {
        return HestiaAPI.instance.getTagPrefix(player.getUniqueId());
    }
    
    @Override
    public int getWeight(UUID uuid) {
        return 0;
    }
}
