package factionsystem.Objects;

import org.bukkit.entity.Player;

import factionsystem.Main;
import net.md_5.bungee.api.ChatColor;

public class QuestNodeAction {
	
	public String name = "";
	public String description = "";
	public int Qty = 0;
	public String command = "";
	public Type type = null; 
	private Main main;
	public enum Type { Command, GiveCoins, TakeCoins, AddToken, RemoveToken }
	
	public QuestNodeAction(Main plugin, Type nodeType)
	{
		main = plugin;
		type = nodeType;
	}
	
	public void Execute(Player player)
	{
		if (type.equals(Type.Command))
		{
			if (!main.getServer().dispatchCommand(main.getServer().getConsoleSender(), command))
			{
				player.sendMessage(ChatColor.RED + "QuestNodeAction Error: Could not execute command:\n" + command);
			}
		}
	}

}
