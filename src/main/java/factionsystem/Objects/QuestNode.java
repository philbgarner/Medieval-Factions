package factionsystem.Objects;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import factionsystem.Main;
import factionsystem.Subsystems.UtilitySubsystem;

public class QuestNode {

	public UUID uuid = UUID.randomUUID();
	public String name = "";
	public String description = "";
	public QuestNode nextNode = null;
	public ArrayList<QuestNodeCondition> conditions = new ArrayList<QuestNodeCondition>();
	public ArrayList<QuestNodeAction> actions = new ArrayList<QuestNodeAction>();
	
	private Main main;
	
	public boolean ProcessNode(Player player)
	{
		boolean result = false;
		for (QuestNodeCondition condition : conditions)
		{
			if (condition.comparison.equals(QuestNodeCondition.LogicalComparison.And))
			{
				result = result && condition.Evaluate(player);
			}
			else
			{
				result = result || condition.Evaluate(player);
			}
		}
		if (result)
		{
			PlayerActivityRecord rec = UtilitySubsystem.getPlayerActivityRecord(player.getUniqueId(), main.playerActivityRecords);
			rec.activeQuestNode.remove(uuid);
			if (nextNode != null)
			{
				for (QuestNodeAction action : actions)
				{
					action.Execute(player);
				}
				player.sendMessage(ChatColor.GREEN + "You have completed " + name + ", now starting " + nextNode.name);
				rec.activeQuestNode.add(nextNode.uuid);
				return true;
			}
			else
			{
				// TODO: Quest complete!
				for (QuestNodeAction action : actions)
				{
					action.Execute(player);
				}
				player.sendMessage(ChatColor.GREEN + "You have completed the quest!");
				return true;
			}
		}
		return false;
	}
	
}
