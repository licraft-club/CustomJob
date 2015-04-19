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
			config.createSection("setting.job-5");
			config.createSection("setting.sub-job-1");
			config.createSection("setting.sub-job-2");
			config.createSection("setting.sub-job-3");
			config.createSection("setting.sub-job-4");
			config.createSection("setting.sub-job-5");
			config.addDefault("menu.title","XX������תְϵͳ");
			config.addDefault("nick.job-1","��ʦ");
			config.addDefault("nick.job-2", "սʿ");
			config.addDefault("nick.job-3","������");
			config.addDefault("nick.job-4","����");
			config.addDefault("nick.job-5", "��ʦ");
			config.addDefault("nick.sub-job-1","��ʦ����");
			config.addDefault("nick.sub-job-2", "սʿ����");
			config.addDefault("nick.sub-job-3", "�����ֶ���");
			config.addDefault("nick.sub-job-4", "���˶���");
			config.addDefault("nick.sub-job-5", "��ʦ����");
			config.addDefault("color.job-1","AQUA");
			config.addDefault("color.job-2", "AQUA");
			config.addDefault("color.job-3", "GREEN");
			config.addDefault("color.job-4","BLUE");
			config.addDefault("color.job-5", "BLUE");
			config.addDefault("color.sub-job-1","AQUA");
			config.addDefault("color.sub-job-2","AQUA");
			config.addDefault("color.sub-job-3","GREEN");
			config.addDefault("color.sub-job-4","BLUE");
			config.addDefault("color.sub-job-5","BLUE");
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
