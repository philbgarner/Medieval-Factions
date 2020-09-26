package factionsystem.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import factionsystem.Main;

public class QuestCommand {

	Main main = null;
	
	public QuestCommand(Main plugin)
	{
		main = plugin;
	}
	
	public void handleQuest(CommandSender sender, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if (args.length > 1)
			{
				if (args[1].equalsIgnoreCase("active"))
				{
					// TODO Iterate and list active quests and that quests' current node.
				}
			}
			else
			{
				// TODO Command usage.
			}
		}
	}
	
}
