package factionsystem.Objects;

import java.util.ArrayList;

public class QuestGoalCondition {
	
	public enum LogicalComparison { And, Or } 

	public LogicalComparison comparison = LogicalComparison.And;
	public QuestNodeGoal.Type goalType = QuestNodeGoal.Type.Gather; 
	public ArrayList<QuestNodeGoalReward> reward = new ArrayList<QuestNodeGoalReward>();
}
