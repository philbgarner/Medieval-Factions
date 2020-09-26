package factionsystem.Objects;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import factionsystem.Main;
import factionsystem.Subsystems.UtilitySubsystem;

public class Quest {

	public UUID uuid = UUID.randomUUID();
	public Player player = null;
	public String name;
	public String desc;
	public int timeLimit;
	public UUID startNodeUUID = null;
	public ArrayList<QuestNode> questNodes = new ArrayList<QuestNode>();
	
	private Main main;
	
	public QuestNode getCurrentNode(Player player)
	{
		PlayerActivityRecord rec = UtilitySubsystem.getPlayerActivityRecord(player.getUniqueId(), main.playerActivityRecords);
		if (rec != null)
		{
			for (QuestNode node : questNodes)
			{
				if (rec.activeQuestNode.contains(node.uuid))
				{
					return node;
				}
			}
		}
		return null;
	}
	
	public Quest(Main plugin)
	{
		main = plugin;
	}
	
}
