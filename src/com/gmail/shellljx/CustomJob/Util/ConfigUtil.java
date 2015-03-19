package com.gmail.shellljx.CustomJob.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.shellljx.CustomJob.CustomJob;

public class ConfigUtil {
	
	FileConfiguration config;
	
	//private CustomJob plugin;
	
	public ConfigUtil (){
		//this.plugin = plugin;
		config = CustomJob.instance.getConfig();
	}
	
	public void createConfig(){
		if(!new File(CustomJob.instance.getDataFolder()+File.separator+"config.yml").exists()){
			config.createSection("setting.job-1");
			config.createSection("setting.job-2");
			config.createSection("setting.job-3");
			config.createSection("setting.job-4");
			config.addDefault("menu.title","XX服务器转职系统");
			config.addDefault("nick.job-1","法师");
			config.addDefault("nick.job-2", "战士");
			config.addDefault("nick.job-3","弓箭手");
			config.addDefault("nick.job-4","猎人");
			config.options().copyDefaults(true);
			CustomJob.instance.saveConfig();
			//CustomJob.instance.saveConfig();
			//CustomJob.instance.reloadConfig();
		}
	}
	
	public void upLoadConfig(String job,HashMap<String,List<Integer>> map){
		config.set(job, null);
		config.addDefault(job, map.get(job));
		config.options().copyDefaults(true);
		CustomJob.instance.saveConfig();
		
	}
	
	
}
