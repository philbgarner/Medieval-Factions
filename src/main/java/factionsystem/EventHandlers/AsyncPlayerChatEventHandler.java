package factionsystem.EventHandlers;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import factionsystem.Objects.NPCConversation;
import factionsystem.Objects.NPCConversation.DialogOption;
import factionsystem.Subsystems.UtilitySubsystem;

import org.bukkit.ChatColor;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static factionsystem.Subsystems.UtilitySubsystem.getPlayersFaction;
import static factionsystem.Subsystems.UtilitySubsystem.sendAllPlayersInFactionMessage;

public class AsyncPlayerChatEventHandler {

    Main main = null;

    public AsyncPlayerChatEventHandler(Main plugin) {
        main = plugin;
    }

    public void handle(AsyncPlayerChatEvent event) {

		if (!UtilitySubsystem.isConversing(event.getPlayer(), main))
		{
	    	if (event.getMessage().equalsIgnoreCase("bob"))
	    	{
				event.getPlayer().sendMessage(ChatColor.WHITE + "<" + event.getPlayer().getName() + "> " + ChatColor.GRAY + "'" + event.getMessage() + "'");
    			NPCConversation.StartConversation(event.getPlayer(), main, main.storage.loadConversation("test"));
    			event.setCancelled(true);
    			return;
			}
    	}
		else
		{
			NPCConversation conversation = UtilitySubsystem.getConversation(event.getPlayer(), main);
			if (conversation.getCurrentSpeech().optionsContainPartial(event.getMessage()))
			{
				DialogOption response = conversation.getCurrentSpeech().optionsPartialMatch(event.getMessage());
				event.getPlayer().sendMessage(conversation.getCurrentSpeech().getFormattedPrefix() + ChatColor.WHITE + "<" + event.getPlayer().getName() + "> " + ChatColor.GRAY + "'" + response.text + "'");
				if (response.nextSpeech != null)
				{
					conversation.setCurrentSpeech(response.nextSpeech);
					conversation.SendSpeech();
				}
				else
				{
					main.npcPlayerConversations.remove(event.getPlayer().getUniqueId());
					event.getPlayer().sendMessage(ChatColor.GREEN + "Conversation ended.");
				}
				event.setCancelled(true);
				return;
			}
			else
			{
				event.getPlayer().sendMessage(ChatColor.RED + "Invalid response, please use one of these responses below:");
				event.getPlayer().sendMessage(conversation.getCurrentSpeech().formatOptions());
				event.setCancelled(true);
				return;
			}
		}
        if (main.playersInFactionChat.contains(event.getPlayer().getUniqueId())) {
            Faction playersFaction = getPlayersFaction(event.getPlayer().getUniqueId(), main.factions);
            if (playersFaction != null) {
                String message = event.getMessage();
                sendAllPlayersInFactionMessage(playersFaction, ChatColor.WHITE + "" + event.getPlayer().getName() + ": " + ChatColor.GOLD + message);
                event.setCancelled(true);
            }
        }
    }

}
