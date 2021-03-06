package factionsystem.Commands;

import static factionsystem.Subsystems.UtilitySubsystem.createStringFromFirstArgOnwards;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import factionsystem.Objects.Gate;
import factionsystem.Subsystems.UtilitySubsystem;

public class GateCommand {

	Main main = null;
	
	public GateCommand(Main plugin)
	{
		main = plugin;
	}
	
	public void handleGate(CommandSender sender, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if (player.hasPermission("mf.gate") || player.hasPermission("mf.default"))
			{
				if (args.length > 1)
				{
					if (args[1].equalsIgnoreCase("cancel"))
					{
						if (main.creatingGatePlayers.containsKey(player.getUniqueId()))
						{
							main.creatingGatePlayers.remove(player.getUniqueId());
							player.sendMessage(ChatColor.RED + "Creating gate cancelled!");
							return;
						}
					}
					else if (args[1].equalsIgnoreCase("list"))
					{
						Faction faction = UtilitySubsystem.getPlayersFaction(player.getUniqueId(), main.factions);
						if (faction != null)
						{
							if (faction.getGates().size() > 0)
							{
								player.sendMessage(ChatColor.AQUA + "Faction Gates");
								for (Gate gate : faction.getGates())
								{
									player.sendMessage(ChatColor.AQUA + String.format("%s: %s", gate.getName(), gate.coordsToString()));
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "Your fation has no gates defined.");
								return;
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + String.format("You are not a member of any faction."));
							return;
						}
					}
					else if (args[1].equalsIgnoreCase("remove"))
					{
						if (player.getTargetBlock(null, 16) != null)
						{
							if (UtilitySubsystem.isGateBlock(player.getTargetBlock(null, 16), main.factions))
							{
								Gate gate = UtilitySubsystem.getGate(player.getTargetBlock(null, 16), main.factions);
								Faction faction = UtilitySubsystem.getGateFaction(gate, main.factions);
								if (faction != null)
								{
									if (!faction.isOfficer(player.getUniqueId()) || !faction.isOwner(player.getUniqueId()))
									{
										player.sendMessage(ChatColor.RED + "You must be a faction officer or owner to use this command.");
										return;
									}
									player.sendMessage(ChatColor.AQUA + String.format("Removed gate '%s'.", gate.getName()));
									return;
								}
								else
								{
									player.sendMessage(ChatColor.RED + String.format("Error: Could not find gate faction.", gate.getName()));
									return;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "Target block is not part of a gate.");
								return;
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + "No block detected to check for gate.");
							return;
						}
					}
					else if (args[1].equalsIgnoreCase("name"))
					{						
						if (player.getTargetBlock(null, 16) != null)
						{
							if (UtilitySubsystem.isGateBlock(player.getTargetBlock(null, 16), main.factions))
							{
								Gate gate = UtilitySubsystem.getGate(player.getTargetBlock(null, 16), main.factions);
								if (args.length > 2)
								{
									Faction faction = UtilitySubsystem.getGateFaction(gate, main.factions);
									if (faction != null)
									{
										if (!faction.isOfficer(player.getUniqueId()) || !faction.isOwner(player.getUniqueId()))
										{
											player.sendMessage(ChatColor.RED + "You must be a faction officer or owner to use this command.");
											return;
										}
										String name = UtilitySubsystem.createStringFromArgIndexOnwards(2, args);
										gate.setName(name);
										player.sendMessage(ChatColor.AQUA + String.format("Changed gate name to '%s'.", gate.getName()));
										return;
									}
									else
									{
										player.sendMessage(ChatColor.RED + "Error: Could not find gate's faction.");
										return;
									}
								}
								else
								{
									player.sendMessage(ChatColor.AQUA + String.format("That is the '%s' gate.", gate.getName()));
									return;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "Target block is not part of a gate.");
								return;
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + "No block detected to check for gate.");
							return;
						}
					}						
				}
				else
				{
			        sender.sendMessage(ChatColor.RED + "Sub-commands:");
			        sender.sendMessage(ChatColor.RED + "/mf gate name");
			        sender.sendMessage(ChatColor.RED + "/mf gate list");
			        sender.sendMessage(ChatColor.RED + "/mf gate remove");
			        sender.sendMessage(ChatColor.RED + "/mf gate cancel");
			        return;
				}
			}
            else {
                player.sendMessage(ChatColor.RED + "Sorry! In order to use this command you need the following permission: 'mf.gate'");
            }

		}
	}
	
}
