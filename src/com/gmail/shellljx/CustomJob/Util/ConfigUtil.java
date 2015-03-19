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
			CustomJob.instance.getConfig().createSection("setting.job-1");
			CustomJob.instance.getConfig().createSection("setting.job-2");
			CustomJob.instance.getConfig().createSection("setting.job-3");
			CustomJob.instance.getConfig().createSection("setting.job-4");
			CustomJob.instance.getConfig().addDefault("menu.title","XX������תְϵͳ");
			CustomJob.instance.getConfig().addDefault("setting.nick.job-1","��ʦ");
			CustomJob.instance.getConfig().addDefault("setting.nick.job-2", "սʿ");
			CustomJob.instance.getConfig().addDefault("setting.nick.job-3","������");
			CustomJob.instance.getConfig().addDefault("setting.nick.job-4","����");
			CustomJob.instance.getConfig().options().copyDefaults(true);
			CustomJob.instance.saveConfig();
			//CustomJob.instance.saveConfig();
			//CustomJob.instance.reloadConfig();
		}
	}
	
	public void removNode(String path){
		System.out.println(path);
		CustomJob.instance.getConfig().set(path, null);
		CustomJob.instance.saveConfig();
		//CustomJob.instance.reloadConfig();
		System.out.println("�Ƴ���Ʒ�ɹ�!");
	}
	
	public void createItem(String path,int id){
		System.out.println(path);
		CustomJob.instance.getConfig().addDefault(path, id);
		CustomJob.instance.getConfig().options().copyDefaults(true);
		CustomJob.instance.saveConfig();
		CustomJob.instance.reloadConfig();
		System.out.println("�����Ʒ�ɹ�!");
	}
	
	public HashMap<String,Object> getItemMap(String path){
		Map<String, Object> map = new HashMap<>();
		//���map��key��ÿһ��ֵ������section,����setting.job-1.item1
		map=config.getConfigurationSection(path).getValues(true);
		return (HashMap<String, Object>) map;
	}
	
}
