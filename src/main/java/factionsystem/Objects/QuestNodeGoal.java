package factionsystem.Objects;

import java.util.ArrayList;

public class QuestNodeGoal {

	public enum Type { Kill, Gather, Escort, Deliver, Defend, Profit, Activate, Search };
	public String name = "";
	public String desc = "";
	public ArrayList<QuestNodeCondition> goals = new ArrayList<QuestNodeCondition>();
	
}
