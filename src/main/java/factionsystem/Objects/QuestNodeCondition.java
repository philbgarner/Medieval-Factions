package factionsystem.Objects;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuestNodeCondition {
	
	public enum LogicalComparison { And, Or } 

	public LogicalComparison comparison = LogicalComparison.And;
	public QuestNodeGoal.Type goalType = QuestNodeGoal.Type.Gather; 
	public ArrayList<QuestNodeGoalReward> reward = new ArrayList<QuestNodeGoalReward>();
	public QuestNode nextNode = null;
	
	public String itemName = "";
	public int qty = 0;
	
	public boolean GatherCondition(Player player)
	{
		Material matType = Material.getMaterial(itemName);
		if (matType != null)
		{
			if (player.getInventory().contains(new ItemStack(matType, qty)))
			{
				player.getInventory().remove(new ItemStack(matType, qty));
				return true;
			}
		}
		return false;
	}
	
	public boolean Evaluate(Player player)
	{
		if (goalType.equals(QuestNodeGoal.Type.Gather))
		{
			return GatherCondition(player);
		}
		return false;
	}
}
