package com.hysteria.practice.shop.buttons;

import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.shop.impl.killeffects.menu.KillEffectsShopMenu;
import com.hysteria.practice.utilities.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import com.hysteria.practice.utilities.menu.Button;

import java.util.ArrayList;
import java.util.List;

public class KillEffectsShopButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&eClick here to purchase Kill Effects!");

        return new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .name("&9&lBuy Kill Effects")
                .lore(lore)
                .build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        Profile profile = Profile.get(player.getUniqueId());
        new KillEffectsShopMenu().openMenu(player);
    }
}

