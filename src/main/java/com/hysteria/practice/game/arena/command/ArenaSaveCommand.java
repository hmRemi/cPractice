package com.hysteria.practice.game.arena.command;

import com.hysteria.practice.game.arena.Arena;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenaSaveCommand extends BaseCommand {

	@Command(name = "arena.save", permission = "cpractice.arena.admin")
	@Override
	public void onCommand(CommandArgs commandArgs) {
		Player player = commandArgs.getPlayer();

		Arena.getArenas().forEach(Arena::save);
		player.sendMessage(ChatColor.GREEN + "Saved all arenas!");
	}

}
