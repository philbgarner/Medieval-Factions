package factionsystem.Objects;

import java.util.UUID;

import org.bukkit.entity.Player;

public class Quest {

	public UUID uuid = UUID.randomUUID();
	public Player player = null;
	public String name;
	public String desc;
	public int timeLimit;
	
	public QuestNode startNode = null;
	
}
