package com.hysteria.practice.game.tournament.events;

import com.hysteria.practice.match.participant.MatchGamePlayer;
import com.hysteria.practice.player.profile.participant.alone.GameParticipant;
import com.hysteria.practice.utilities.event.CustomEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TournamentEndEvent extends CustomEvent {

    private final GameParticipant<MatchGamePlayer> winner;
    private final boolean team;
    private final boolean clan;

}