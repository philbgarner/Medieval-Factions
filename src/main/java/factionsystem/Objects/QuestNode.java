package factionsystem.Objects;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class QuestNode {

	public UUID uuid = UUID.randomUUID();
	public String name = "";
	public String description = "";
	public QuestNode parentNode = null;
	public QuestNode nextNode = null;
	public ArrayList<QuestNodeCondition> conditions = new ArrayList<QuestNodeCondition>();
	public ArrayList<QuestNodeGoalReward> reward = new ArrayList<QuestNodeGoalReward>();
	
	public void ProcessNode(Player player)
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
			player.sendMessage(ChatColor.GREEN + "You have completed " + name + ", now starting " + nextNode.name);
		}
	}
	
}
