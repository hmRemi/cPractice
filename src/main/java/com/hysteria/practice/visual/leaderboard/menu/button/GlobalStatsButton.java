package com.hysteria.practice.visual.leaderboard.menu.button;

import com.google.common.collect.Lists;
import com.hysteria.practice.cPractice;
import com.hysteria.practice.visual.leaderboard.Leaderboard;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.utilities.elo.EloUtil;
import com.hysteria.practice.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GlobalStatsButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player){
        List<String> lore = Lists.newArrayList();
        List<Profile> profiles = Leaderboard.getLeaderboards().stream().limit(10).collect(Collectors.toList());

        AtomicInteger pos = new AtomicInteger(0);

        lore.add(CC.MENU_BAR);
        for (Profile leaderboard : profiles) {
            pos.incrementAndGet();
            if (pos.get() == 1) {
                List<String> first = cPractice.get().getLeaderboardConfig().getStringList("INVENTORY.GLOBAL_STATS.POSITIONS.1");
                for (String s : first) {
                    lore.add(s
                            .replace("{pos}", String.valueOf(pos.get()))
                            .replace("{name}", leaderboard.getName())
                            .replace("{color}", leaderboard.getColor())
                            .replace("{data}", String.valueOf(EloUtil.getGlobalElo(leaderboard)))
                            .replace("{bars}", CC.MENU_BAR));
                }
            }
            else if (pos.get() == 2) {
                List<String> second = cPractice.get().getLeaderboardConfig().getStringList("INVENTORY.GLOBAL_STATS.POSITIONS.2");
                for (String s : second) {
                    lore.add(s
                            .replace("{pos}", String.valueOf(pos.get()))
                            .replace("{name}", leaderboard.getName())
                            .replace("{color}", leaderboard.getColor())
                            .replace("{data}", String.valueOf(EloUtil.getGlobalElo(leaderboard)))
                            .replace("{bars}", CC.MENU_BAR));
                }
            }
            else if (pos.get() == 3) {
                List<String> third = cPractice.get().getLeaderboardConfig().getStringList("INVENTORY.GLOBAL_STATS.POSITIONS.3");
                for (String s : third) {
                    lore.add(s
                            .replace("{pos}", String.valueOf(pos.get()))
                            .replace("{name}", leaderboard.getName())
                            .replace("{color}", leaderboard.getColor())
                            .replace("{data}", String.valueOf(EloUtil.getGlobalElo(leaderboard)))
                            .replace("{bars}", CC.MENU_BAR));
                }
            }
            else {
                List<String> another = cPractice.get().getLeaderboardConfig().getStringList("INVENTORY.GLOBAL_STATS.POSITIONS.ANOTHER");
                for (String s : another) {
                    lore.add(s
                            .replace("{pos}", String.valueOf(pos.get()))
                            .replace("{name}", leaderboard.getName())
                            .replace("{color}", leaderboard.getColor())
                            .replace("{data}", String.valueOf(EloUtil.getGlobalElo(leaderboard)))
                            .replace("{bars}", CC.MENU_BAR));
                }
            }
        }
        lore.add(CC.MENU_BAR);

        return new ItemBuilder(Material.NETHER_STAR)
                .name(cPractice.get().getLeaderboardConfig().getString("INVENTORY.GLOBAL_STATS.TITLE"))
                .lore(lore)
                .build();
    }
}
