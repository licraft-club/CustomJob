package com.gmail.shellljx.CustomJob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.shellljx.CustomJob.Util.ConfigUtil;
import com.gmail.shellljx.CustomJob.command.MainCommand;
import com.gmail.shellljx.CustomJob.command.PluginAdminCommand;

public class CustomJob extends JavaPlugin {
	
	private ConfigUtil configUtil;
	private PluginManager pm;
	private HashMap<String,Object> itemMap;
	private HashMap<String,List<Integer>> jobMap;
	private HashMap<String,List<String>> ItemIdMap;
	public static CustomJob instance;
	public Logger log = Logger.getLogger("CustomJob");
	
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		instance = this;
		configUtil = new ConfigUtil();
		itemMap =new HashMap<>();
		jobMap = new HashMap<>();
		ItemIdMap = new HashMap<>();
		configUtil.createConfig();
		initJob("setting.job-1");
		initJob("setting.job-2");
		initJob("setting.job-3");
		initJob("setting.job-4");
		pm = getServer().getPluginManager();
		this.getCommand("custom").setExecutor(new MainCommand(this));
		this.getCommand("cadmin").setExecutor(new PluginAdminCommand(this));
		this.log.info("=====================================================");
		this.log.info("[CustomJob]CustomJob插件加载成功,定制插件请联系QQ：1057645164");
		this.log.info("======================================================");

	}
	
	public ConfigUtil getConfigUtil(){
		return this.configUtil;
	}
	
	public void initJob(String job){
		itemMap = configUtil.getItemMap(job);
		
		jobMap.clear();
		ItemIdMap.clear();
		List<Integer> list = new ArrayList<>();
		List<String> jobIdList = new ArrayList<>();
		System.out.println(itemMap.size());
		for(String key : itemMap.keySet()){
			list.add((Integer) itemMap.get(key));
			jobIdList.add(key);
			System.out.println("...");
		}
		//初始化职业节点下面的武器id List
		jobMap.put(job, list);
		//初始化职业节点下面的武器名字 List
		ItemIdMap.put(job, jobIdList);
	}

	public List<Integer> getItems(String job){
		return jobMap.get(job);
	}
	
	public  List<String> getItemIds(String job){
		return this.ItemIdMap.get(job);
	}
	//获得相应职业节点的物品名称List，比如item1,item2..
	public String getItemKey(String job,int value){
		for(int i=0;i<jobMap.get(job).size();i++){
			if(jobMap.get(job).get(i)==value){
				System.out.println(ItemIdMap.get(job).get(i));
				return ItemIdMap.get(job).get(i);
			}
		}
		return "";
	}


	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
}
