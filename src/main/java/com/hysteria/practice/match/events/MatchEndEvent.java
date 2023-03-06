package com.hysteria.practice.match.events;

import com.hysteria.practice.match.Match;
import com.hysteria.practice.utilities.event.CustomEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MatchEndEvent extends CustomEvent {

    private Match match;
}