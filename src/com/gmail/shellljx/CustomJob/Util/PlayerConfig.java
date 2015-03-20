package com.gmail.shellljx.CustomJob.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.shellljx.CustomJob.CustomJob;

public class PlayerConfig extends YamlConfiguration {

	private File playerConfigFile = null;
	private FileConfiguration playerConfig = null;
	private CustomJob plugin;
	
	public PlayerConfig(CustomJob plugin){
		this.plugin = plugin;
	}
	
	public void reloadPlayerConfig(){
		
		if(playerConfigFile == null){
			playerConfigFile = new File(this.plugin.getDataFolder()+File.separator+"save.yml");
		}
		playerConfig = YamlConfiguration.loadConfiguration(playerConfigFile);
		
		InputStream in = this.plugin.getResource("save.yml");
		if(in!=null){
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(in);
			playerConfig.setDefaults(defConfig);
		}
		
	}
	
	public FileConfiguration getPlayerConfig(){
		if(playerConfig == null){
			reloadPlayerConfig();
		}
		return playerConfig;
	}
	
	public void savePlayerConfig(){
		if(playerConfig==null||playerConfigFile == null){
			return;
		}
		try {
			getPlayerConfig().save(playerConfigFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.plugin.getLogger().log(Level.SEVERE,"Could not save config to "+playerConfigFile,e);
			e.printStackTrace();
		}
	}
	
	public void saveDefaultConfig(){
		if(playerConfigFile == null){
			playerConfigFile = new File(this.plugin.getDataFolder()+File.separator+"save.yml");
		}
		if(!playerConfigFile.exists()){
			this.plugin.saveResource("save.yml", false);
		}
	}
}
