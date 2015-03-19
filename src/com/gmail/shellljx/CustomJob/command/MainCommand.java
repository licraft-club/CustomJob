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
			sender.sendMessage(ChatColor.RED+"��ʹ��/custom help�鿴����");
			return true;
		}
		if(args[0].equalsIgnoreCase("help")&&args.length==1){
			sender.sendMessage(ChatColor.YELLOW+"-----------------.["+ChatColor.RED+this.plugin.getConfig().getString("menu.title")+ChatColor.YELLOW+"].-----------------");
			sender.sendMessage(ChatColor.AQUA+"����:shell,QQ:1057645164");
			sender.sendMessage(ChatColor.AQUA+"/custom trans [ְҵ]  --ת��ְҵ");
			sender.sendMessage(ChatColor.AQUA+"/custom help  --�鿴����");
			sender.sendMessage(ChatColor.AQUA+"/custom help admin  --����Ա�˵�");
			sender.sendMessage(ChatColor.YELLOW+"------------------------------------------------------------------");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("help")&&args[1].equalsIgnoreCase("admin")){
			sender.sendMessage(ChatColor.YELLOW+"-----------------.["+ChatColor.RED+this.plugin.getConfig().getString("menu.title")+ChatColor.YELLOW+"].-----------------");
			sender.sendMessage(ChatColor.AQUA+"/cadmin add [ְҵ]  --��������RPGItem������ĳ��ְҵ");
			sender.sendMessage(ChatColor.AQUA+"/cadmin remove [ְҵ] --��ĳ��ְҵ���Ƴ������е�RPGItem");
			sender.sendMessage(ChatColor.AQUA+"/cadmin reload  --���ز��");
			return true;
		}
		
		return false;
	}

}
