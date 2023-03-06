package com.hysteria.practice.shop.buttons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.shop.impl.trails.menu.TrailEffectsShopMenu;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.menu.Button;

import java.util.ArrayList;
import java.util.List;

public class TrailsEffectsShopButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&eClick here to purchase Trail Effects!");

        return new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .name("&9&lBuy Trail Effects")
                .lore(lore)
                .build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        Profile profile = Profile.get(player.getUniqueId());
        new TrailEffectsShopMenu().openMenu(player);
    }
}

