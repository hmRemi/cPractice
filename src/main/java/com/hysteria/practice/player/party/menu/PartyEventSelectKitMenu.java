package com.hysteria.practice.player.party.menu;

import com.hysteria.practice.game.arena.Arena;
import com.hysteria.practice.game.kit.Kit;
import com.hysteria.practice.match.Match;
import com.hysteria.practice.match.impl.BasicFreeForAllMatch;
import com.hysteria.practice.match.impl.BasicTeamMatch;
import com.hysteria.practice.match.impl.BasicTeamRoundMatch;
import com.hysteria.practice.match.participant.MatchGamePlayer;
import com.hysteria.practice.player.party.Party;
import com.hysteria.practice.player.party.enums.PartyEvent;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.player.profile.participant.alone.GameParticipant;
import com.hysteria.practice.player.profile.participant.team.TeamGameParticipant;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.utilities.menu.Button;
import com.hysteria.practice.utilities.menu.Menu;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@AllArgsConstructor
public class PartyEventSelectKitMenu extends Menu {

	private PartyEvent partyEvent;

	@Override
	public String getTitle(Player player) {
		return "&9Select a kit";
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		Party party = Profile.get(player.getUniqueId()).getParty();

		for (Kit kit : Kit.getKits()) {
			if ((partyEvent == PartyEvent.SPLIT || /*!kit.getGameRules().isBridge() &&*/ party.getListOfPlayers().size() <= 8)) {
				if (kit.isEnabled()) {
					buttons.put(kit.getSlot(), new SelectKitButton(partyEvent, kit));
					//buttons.put(buttons.size(), new SelectKitButton(partyEvent, kit));
				}
//				if (kit.isEnabled() && !kit.getGameRules().isOnlyHCF()) {
//					buttons.put(buttons.size(), new SelectKitButton(partyEvent, kit));
//				}
			}
		}

		return buttons;
	}

	@AllArgsConstructor
	private class SelectKitButton extends Button {

		private PartyEvent partyEvent;
		private Kit kit;

		@Override
		public ItemStack getButtonItem(Player player) {
			return new ItemBuilder(kit.getDisplayIcon())
					.addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
					.addItemFlag(ItemFlag.HIDE_ENCHANTS)
					.addItemFlag(ItemFlag.HIDE_POTION_EFFECTS)
					.name("&a" + kit.getName())
					.build();
		}

		@Override
		public void clicked(Player player, ClickType clickType) {
			Menu.currentlyOpenedMenus.get(player.getName()).setClosedByMenu(true);

			player.closeInventory();

			Profile profile = Profile.get(player.getUniqueId());

			if (profile.getParty() == null) {
				player.sendMessage(CC.RED + "You are not in a party.");
				return;
			}

			if (profile.getParty().getPlayers().size() <= 1) {
				player.sendMessage(CC.RED + "You do not have enough players in your party to start an event.");
				return;
			}

			Party party = profile.getParty();
			Arena arena = Arena.getRandomArena(kit);

			if (arena == null) {
				player.sendMessage(CC.RED + "There are no available arenas.");
				return;
			}

			arena.setActive(true);

			Match match;

			if (partyEvent == PartyEvent.FFA) {
				List<GameParticipant<MatchGamePlayer>> participants = new ArrayList<>();

				for (Player partyPlayer : party.getListOfPlayers()) {
					participants.add(new GameParticipant<>(
							new MatchGamePlayer(partyPlayer.getUniqueId(), partyPlayer.getName())));
				}

				match = new BasicFreeForAllMatch(null, kit, arena, participants);
			} else {
				Player partyLeader = party.getLeader();
				Player randomLeader = Bukkit.getPlayer(party.getPlayers().get(1));

				MatchGamePlayer leaderA = new MatchGamePlayer(partyLeader.getUniqueId(), partyLeader.getName());
				MatchGamePlayer leaderB = new MatchGamePlayer(randomLeader.getUniqueId(), randomLeader.getName());

				GameParticipant<MatchGamePlayer> participantA = new TeamGameParticipant<>(leaderA);
				GameParticipant<MatchGamePlayer> participantB = new TeamGameParticipant<>(leaderB);

				List<Player> players = new ArrayList<>(party.getListOfPlayers());
				Collections.shuffle(players);

				for (Player otherPlayer : players) {
					if (participantA.containsPlayer(otherPlayer.getUniqueId()) ||
					    participantB.containsPlayer(otherPlayer.getUniqueId())) {
						continue;
					}

					MatchGamePlayer gamePlayer = new MatchGamePlayer(otherPlayer.getUniqueId(), otherPlayer.getName());

					if (participantA.getPlayers().size() > participantB.getPlayers().size()) {
						participantB.getPlayers().add(gamePlayer);
					} else {
						participantA.getPlayers().add(gamePlayer);
					}
				}

				// Create match
				if (kit.getGameRules().isBridge()) match = new BasicTeamRoundMatch(null, kit, arena, false, participantA, participantB, 3);
				else match = new BasicTeamMatch(null, kit, arena, false, participantA, participantB);
			}

			// Start match
			match.start();
		}

	}

}
