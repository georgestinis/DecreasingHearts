package com.jaymun;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class HeartsDecreasingPlugin extends JavaPlugin{
	
	private static HeartsDecreasingPlugin instance;
	private double hearts = 20;
	static int taskId = -1;
	
	public static HeartsDecreasingPlugin getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		BukkitScheduler scheduler = getServer().getScheduler();
		for (Player p : getServer().getOnlinePlayers()) {
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hearts);
		}
		taskId = scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for (Player p : getServer().getOnlinePlayers()) {
					if (p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 2) {
						p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2);
						getServer().getScheduler().cancelTask(HeartsDecreasingPlugin.taskId);
					}
					else {
						p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hearts);
						hearts -= 2;
					}
				}
			}
			
		}, 0L, 6000L);//
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}

}
