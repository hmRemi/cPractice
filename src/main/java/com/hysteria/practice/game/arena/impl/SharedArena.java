package com.hysteria.practice.game.arena.impl;

import com.hysteria.practice.game.arena.Arena;
import com.hysteria.practice.game.arena.ArenaType;
import com.hysteria.practice.cPractice;
import com.hysteria.practice.utilities.LocationUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;

@Getter
@Setter
public class SharedArena extends Arena {

	public SharedArena(String name, Location location1, Location location2) {
		super(name, location1, location2);
	}

	@Override
	public ArenaType getType() {
		return ArenaType.SHARED;
	}

	@Override
	public void save() {
		String path = "arenas." + getName();

		FileConfiguration configuration = cPractice.get().getArenasConfig().getConfiguration();
		configuration.set(path, null);
		configuration.set(path + ".type", getType().name());
		configuration.set(path + ".icon.material", getDisplayIcon().getType().name());
		configuration.set(path + ".icon.durability", getDisplayIcon().getDurability());
		configuration.set(path + ".spawnA", LocationUtil.serialize(spawnA));
		configuration.set(path + ".spawnB", LocationUtil.serialize(spawnB));
		configuration.set(path + ".cuboid.location1", LocationUtil.serialize(getLowerCorner()));
		configuration.set(path + ".cuboid.location2", LocationUtil.serialize(getUpperCorner()));
		configuration.set(path + ".kits", getKits());
		configuration.set(path + ".author", getAuthor());

		try {
			configuration.save(cPractice.get().getArenasConfig().getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {
		super.delete();

		FileConfiguration configuration = cPractice.get().getArenasConfig().getConfiguration();
		configuration.set("arenas." + getName(), null);

		try {
			configuration.save(cPractice.get().getArenasConfig().getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
