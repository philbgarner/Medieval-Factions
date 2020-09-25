package factionsystem.Objects;

import java.util.ArrayList;
import java.util.UUID;

public class QuestNode {

	public UUID uuid = UUID.randomUUID();
	public QuestNode parentNode = null;
	public ArrayList<QuestNodeCondition> conditions = new ArrayList<QuestNodeCondition>();
	
	public boolean isNodeCompleted()
	{
		// TODO: Run through quest node conditions and return the result.
		boolean cond = false;
		return cond;
	}
	
}
