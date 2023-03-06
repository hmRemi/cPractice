package com.hysteria.practice.game.tournament.commands.subcommands;

import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.player.profile.ProfileState;
import com.hysteria.practice.player.profile.hotbar.Hotbar;
import com.hysteria.practice.game.tournament.Tournament;
import com.hysteria.practice.game.tournament.TournamentState;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TournamentStopCommand extends BaseCommand {

    @Command(name = "tournament.stop", permission = "tournament.admin")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        Tournament<?> tournament = Tournament.getTournament();
        if (tournament == null || tournament.getState() == TournamentState.ENDED) {
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(ChatColor.RED + "No tournament found.");
            player.sendMessage(CC.CHAT_BAR);
            return;
        }
        if ((tournament.getState() == TournamentState.IN_FIGHT || tournament.getState() == TournamentState.SELECTING_DUELS)
                && tournament.getTeams().size() == 1) {
            tournament.end(tournament.getTeams().get(0));
            return;
        }
        tournament.end(null);

        Profile profile = Profile.get(player.getUniqueId());
        profile.setState(ProfileState.LOBBY);
        Hotbar.giveHotbarItems(player);
    }
}