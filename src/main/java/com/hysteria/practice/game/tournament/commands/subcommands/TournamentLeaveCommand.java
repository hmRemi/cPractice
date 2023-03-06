package com.hysteria.practice.game.tournament.commands.subcommands;
/*
   Made by cpractice Development Team
   Created on 10.10.2021
*/

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import com.hysteria.practice.cPractice;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.player.profile.ProfileState;
import com.hysteria.practice.player.profile.hotbar.Hotbar;
import com.hysteria.practice.game.tournament.Tournament;
import com.hysteria.practice.game.tournament.TournamentState;
import com.hysteria.practice.utilities.PlayerUtil;

public class TournamentLeaveCommand extends BaseCommand {

    @Command(name = "tournament.leave")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        Profile profile = Profile.get(player.getUniqueId());
        Tournament<?> tournament = Tournament.getTournament();
        if (tournament == null || tournament.getState() == TournamentState.ENDED || !profile.isInTournament()) {
            player.sendMessage(ChatColor.RED + "No tournament found.");
            return;
        }

        tournament.removePlayer(player.getUniqueId());
        tournament.getPlayers().remove(player.getUniqueId());

        // Reset players' state
        profile.setState(ProfileState.LOBBY);
        profile.setInTournament(false);

        PlayerUtil.reset(player);
        player.teleport(cPractice.get().getEssentials().getSpawn());
        Hotbar.giveHotbarItems(player);
    }
}
