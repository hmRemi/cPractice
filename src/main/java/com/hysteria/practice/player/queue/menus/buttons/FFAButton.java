package com.hysteria.practice.player.queue.menus.buttons;
/* 
   Made by cpractice Development Team
   Created on 30.11.2021
*/

import com.google.common.collect.Lists;
import com.hysteria.practice.game.arena.Arena;
import com.hysteria.practice.cPractice;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.utilities.file.type.BasicConfigurationFile;
import com.hysteria.practice.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class FFAButton extends Button {

    BasicConfigurationFile config = cPractice.get().getMainConfig();

    @Override
    public ItemStack getButtonItem(Player player) {
        ArrayList<String> lore = Lists.newArrayList();
        for (String text : this.config.getStringList("QUEUES.TYPES.FFA.LORE")) {
            lore.add(text.replace("<kit>", cPractice.get().getMainConfig().getString("FFA.KIT")).replace("<players>", String.valueOf(cPractice.get().getFfaManager().getFFAPlayers().size())));
        }
        return new ItemBuilder(Material.valueOf(cPractice.get().getMainConfig().getString("QUEUES.TYPES.FFA.ICON"))).lore(CC.translate(lore)).amount(1).name(CC.translate(cPractice.get().getMainConfig().getString("QUEUES.TYPES.FFA.NAME"))).durability(cPractice.get().getMainConfig().getInteger("QUEUES.TYPES.FFA.DATA")).build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        cPractice.get().getFfaManager().joinFFA(player, Arena.getByName("FFA"));
    }
}

