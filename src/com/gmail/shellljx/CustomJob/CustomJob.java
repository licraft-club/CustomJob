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
		this.getCommand("cjadmin").setExecutor(new PluginAdminCommand(this));
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
		jobMap.put("setting.job-5", this.getConfig().getIntegerList("setting.job-5"));
		jobMap.put("setting.sub-job-1",this.getConfig().getIntegerList("setting.sub-job-1"));
		jobMap.put("setting.sub-job-2",this.getConfig().getIntegerList("setting.sub-job-2"));
		jobMap.put("setting.sub-job-3",this.getConfig().getIntegerList("setting.sub-job-3"));
		jobMap.put("setting.sub-job-4",this.getConfig().getIntegerList("setting.sub-job-4"));
		jobMap.put("setting.sub-job-5",this.getConfig().getIntegerList("setting.sub-job-5"));
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
			if(jobMap.get(key).contains(id)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isJob(String job_name){
		for(String key:jobMap.keySet()){
			if(key.equalsIgnoreCase("setting."+job_name))
				return true;
		}
		return false;
	}
	
	public boolean include(String old_job,ItemStack item){
		int item_id = item.getTypeId();
		if(jobMap.get("setting."+old_job).contains(item_id))
			return true;
		return false;
	}
	
	
	public FileConfiguration getPlayerConfig(){
		return this.playerConfig;
	}
	
	public void playerConfigReload(){
		this.pf.reloadPlayerConfig();
		this.playerConfig = pf.getPlayerConfig();
	}
	
	public void playerConfigSave(){
		this.pf.savePlayerConfig();
		this.playerConfig = pf.getPlayerConfig();
	}
	
	public void joinAJob(String path,String job_name){
		playerConfig.addDefault(path, job_name);
		playerConfig.options().copyDefaults(true);
		pf.savePlayerConfig();
		pf.reloadPlayerConfig();
		playerConfig = pf.getPlayerConfig();
	}
	
	public void transJob(String path,String job_name){
		playerConfig.set(path, job_name);
		playerConfig.options().copyDefaults(true);
		pf.savePlayerConfig();
		pf.reloadPlayerConfig();
		playerConfig = pf.getPlayerConfig();
	}
	
	public void clearJob(String path){
		playerConfig.set(path, null);
		playerConfig.options().copyDefaults(true);
		pf.savePlayerConfig();
		pf.reloadPlayerConfig();
		playerConfig = pf.getPlayerConfig();
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		this.pf.savePlayerConfig();
		this.saveConfig();
		this.log.info("=====================================================");
		this.log.info("[CustomJob]CustomJob插件加载成功,定制插件请联系QQ：1057645164");
		this.log.info("======================================================");
	}
}
