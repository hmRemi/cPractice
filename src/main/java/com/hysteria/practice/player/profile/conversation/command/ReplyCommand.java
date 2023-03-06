package com.hysteria.practice.player.profile.conversation.command;

import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.player.profile.conversation.Conversation;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReplyCommand extends BaseCommand {

    @Command(name = "reply", aliases = {"r"})
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.RED + "Please usage: /" + commandArgs.getLabel() + " (message)");
            return;
        }

        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }

        Profile playerProfile = Profile.get(player.getUniqueId());
        Conversation conversation = playerProfile.getConversations().getLastRepliedConversation();

        if (conversation != null) {
            if (conversation.validate()) {
                conversation.sendMessage(player, Bukkit.getPlayer(conversation.getPartner(player.getUniqueId())), message.toString());
            } else {
                player.sendMessage(CC.RED + "You can no longer reply to that player.");
            }
        } else {
            player.sendMessage(CC.RED + "You have nobody to reply to.");
        }
    }
}
