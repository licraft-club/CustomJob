package com.gmail.shellljx.CustomJob.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.shellljx.CustomJob.CustomJob;

public class MainCommand implements CommandExecutor {

	private final CustomJob plugin;
	public MainCommand(CustomJob plugin){
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// TODO Auto-generated method stub
		if(args.length==0){
			sender.sendMessage(ChatColor.RED+"请使用/custom help查看帮助");
			return true;
		}
		if(args[0].equalsIgnoreCase("help")&&args.length==1){
			sender.sendMessage(ChatColor.YELLOW+"-----------------.["+ChatColor.RED+this.plugin.getConfig().getString("menu.title")+ChatColor.YELLOW+"].-----------------");
			sender.sendMessage(ChatColor.AQUA+"作者:shell,QQ:1057645164");
			sender.sendMessage(ChatColor.AQUA+"/custom trans [职业]  --转换职业");
			sender.sendMessage(ChatColor.AQUA+"/custom help  --查看帮助");
			sender.sendMessage(ChatColor.AQUA+"/custom help admin  --管理员菜单");
			sender.sendMessage(ChatColor.YELLOW+"------------------------------------------------------------------");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("help")&&args[1].equalsIgnoreCase("admin")){
			sender.sendMessage(ChatColor.YELLOW+"-----------------.["+ChatColor.RED+this.plugin.getConfig().getString("menu.title")+ChatColor.YELLOW+"].-----------------");
			sender.sendMessage(ChatColor.AQUA+"/cadmin add [职业]  --添加手里的RPGItem武器到某个职业");
			sender.sendMessage(ChatColor.AQUA+"/cadmin remove [职业] --从某个职业中移除你手中的RPGItem");
			sender.sendMessage(ChatColor.AQUA+"/cadmin reload  --重载插件");
			return true;
		}
		
		return false;
	}

}
