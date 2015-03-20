package com.gmail.shellljx.CustomJob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.shellljx.CustomJob.Listener.PlayerListener;
import com.gmail.shellljx.CustomJob.Util.ConfigUtil;
import com.gmail.shellljx.CustomJob.Util.PlayerConfig;
import com.gmail.shellljx.CustomJob.command.MainCommand;
import com.gmail.shellljx.CustomJob.command.PluginAdminCommand;

public class CustomJob extends JavaPlugin {
	
	private ConfigUtil configUtil;
	private PlayerConfig pf;
	private PluginManager pm;
	public static CustomJob instance;
	private List<Integer> items = new ArrayList<>();
	private HashMap<String,List<Integer>> jobMap = new HashMap<>();
	private HashMap<String,Integer> playerJob = new HashMap<>();
	public Logger log = Logger.getLogger("CustomJob");
	private PlayerListener pl = new PlayerListener(this);
	private FileConfiguration playerConfig = null;
	
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		instance = this;
		configUtil = new ConfigUtil();
		configUtil.createConfig();
		pf = new PlayerConfig(this);
		playerConfig = pf.getPlayerConfig();
		pm = getServer().getPluginManager();
		pm.registerEvents(pl, this);
		this.getCommand("custom").setExecutor(new MainCommand(this));
		this.getCommand("cadmin").setExecutor(new PluginAdminCommand(this));
		this.log.info("=====================================================");
		this.log.info("[CustomJob]CustomJob插件加载成功,定制插件请联系QQ：1057645164");
		this.log.info("======================================================");
		initItems();
	}
	
	public void initItems(){
		jobMap.put("setting.job-1",this.getConfig().getIntegerList("setting.job-1"));
		jobMap.put("setting.job-2", this.getConfig().getIntegerList("setting.job-2"));
		jobMap.put("setting.job-3", this.getConfig().getIntegerList("setting.job-3"));
		jobMap.put("setting.job-4", this.getConfig().getIntegerList("setting.job-4"));
	}
	
	public List<Integer> getitems(String job){
		return jobMap.get(job);
	}
	
	public boolean createItem(String job,int id){
		System.out.println(jobMap.get("setting.job-1").size());
		if(!jobMap.get(job).contains(id)){
			jobMap.get(job).add(id);
			this.configUtil.upLoadConfig(job,jobMap);
			return true;
		}
		return false;
	}
	
	public boolean removeItem(String job,int id){
		if(jobMap.get(job).contains(id)){
			jobMap.get(job).remove(new Integer(id));
			this.configUtil.upLoadConfig(job,jobMap);
			return true;
		}
		return false;
	}
	
	public boolean isJobItem(int id){
		for(String key:jobMap.keySet()){
			if(jobMap.get(key).contains(id))
				return true;
		}
		return false;
	}
	
	public FileConfiguration getPlayerConfig(){
		return this.playerConfig;
	}
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		this.pf.savePlayerConfig();
	}
}
