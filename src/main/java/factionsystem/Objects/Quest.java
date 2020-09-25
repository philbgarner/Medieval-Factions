package factionsystem.Objects;

import java.util.UUID;

public class Quest {

	public UUID uuid = UUID.randomUUID();
	public String name;
	public String desc;
	public int timeLimit;
	
	public QuestNode startNode = null;
	
}
