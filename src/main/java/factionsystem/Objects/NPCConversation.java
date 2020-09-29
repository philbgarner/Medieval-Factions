package factionsystem.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.ws.soap.AddressingFeature.Responses;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import factionsystem.Main;

public class NPCConversation {
	
	public class DialogPrompt
	{
		private String[] options;
		
		public DialogPrompt(String[] optionsText)
		{
			options = optionsText;
		}
		
		public boolean containsPartial(String s)
		{
			for (String option : options)
			{
				if (option.substring(0, s.length()).equalsIgnoreCase(s))
				{
					return true;
				}
			}
			return false;
		}
		
		public String formatOptionSet()
		{
			String s = ChatColor.DARK_GRAY + "";
			for (String option : options)
			{
				s += "    '" + option + "'\n";
			}
			return s;
		}
		
		public String getPromptText()
		{
			
			return ChatColor.WHITE + "<" + currentSpeech.name + ">" + ChatColor.GRAY + " '" + currentSpeech.text + "':\n" + formatOptionSet();
		}		
	}
	
	public class DialogSpeechJson
	{
		public String name;
		public String text;
		public ArrayList<DialogOptionJson> responses;
	}
	
	public class DialogOptionJson
	{
		public String text;
		public UUID questToken;
		public DialogSpeechJson nextSpeech;
	}

	public class DialogOption
	{
		public String text = "";
		public UUID questToken = null;
		public DialogSpeech nextSpeech = null;
				
		public DialogOption(DialogOptionJson jsonObject)
		{
			text = jsonObject.text;
			questToken = jsonObject.questToken;
			if (jsonObject.nextSpeech != null)
			{
				nextSpeech = new DialogSpeech(jsonObject.nextSpeech);
			}
		}
	}

	public class DialogSpeech
	{
		public String name = "";
		public String text = "";
		public ArrayList<DialogOption> responses = new ArrayList<DialogOption>();
			
		public boolean optionsContainPartial(String s)
		{
			for (DialogOption option : responses)
			{
				if (option.text.substring(0, s.length()).equalsIgnoreCase(s))
				{
					return true;
				}
			}
			return false;
		}

		public DialogOption optionsPartialMatch(String s)
		{
			for (DialogOption option : responses)
			{
				if (option.text.substring(0, s.length()).equalsIgnoreCase(s))
				{
					return option;
				}
			}
			return null;
		}		
		
		public String getFormattedPrefix()
		{
			return ChatColor.YELLOW + "[NPC] " + ChatColor.WHITE;
		}
		
		public String formatOptions()
		{
			String s = "";
			for (DialogOption option : responses)
			{
				s += ChatColor.WHITE + "    -" + ChatColor.GRAY + "'" + option.text + "'\n" + ChatColor.WHITE;
			}
			return s;
		}

		public DialogSpeech(DialogSpeechJson jsonObject)
		{
			name = jsonObject.name;
			text = jsonObject.text;
			for (DialogOptionJson response : jsonObject.responses)
			{
				responses.add(new DialogOption(response));
			}
		}
	}	
	
	private Main main;
	private DialogSpeech currentSpeech = null;
	private DialogSpeech startSpeech = null;
	private Player player = null;
	
	public DialogSpeech getCurrentSpeech()
	{
		return currentSpeech;
	}
	
	public void setCurrentSpeech(DialogSpeech speech)
	{
		currentSpeech = speech;
	}
	
	public void SendSpeech()
	{
		if (player != null)
		{
			player.sendMessage(currentSpeech.getFormattedPrefix() + String.format("<%s> %s", currentSpeech.name, currentSpeech.text));
			if (currentSpeech.responses.size() > 0)
			{
				player.sendMessage(currentSpeech.formatOptions());
			}
			else
			{
				main.npcPlayerConversations.remove(player.getUniqueId());
				player.sendMessage(ChatColor.GREEN + "Conversation ended.");			}
		}
		else
		{
			System.out.println("Cant send speech to player 'null'");
		}
	}
	
	public NPCConversation (DialogSpeechJson conversation, Main plugin)
	{
		main = plugin;
		currentSpeech = new DialogSpeech(conversation);
		startSpeech = currentSpeech;
		ArrayList<String> options = new ArrayList<>();
		for (DialogOption option : currentSpeech.responses)
		{
			options.add(option.text);
		}
		
	}
	
	public static void StartConversation(Player player, Main plugin, NPCConversation conversation)
	{
		if (!plugin.npcPlayerConversations.containsKey(player.getUniqueId()))
		{
			plugin.npcPlayerConversations.put(player.getUniqueId(), conversation);
			conversation.player = player;
			player.sendMessage(ChatColor.GREEN + "Conversation starting.");
			conversation.SendSpeech();
		}
	}
	
}
