package com.gmail.shellljx.CustomJob.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import think.rpgitems.api.RPGItems;
import think.rpgitems.item.RPGItem;

import com.gmail.shellljx.CustomJob.CustomJob;

public class PluginAdminCommand implements CommandExecutor {
	private CustomJob plugin;
	
	public PluginAdminCommand(CustomJob plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// TODO Auto-generated method stub
		if(args.length==2&&sender.isOp()&&args[0].equalsIgnoreCase("add")&&sender instanceof Player){
			Player player = (Player) sender;
			RPGItems rpgItems = new RPGItems();
			ItemStack item = player.getItemInHand();
			//构造装备名称
			int size = plugin.getItems("setting."+args[1]).size();
			StringBuilder builder = new StringBuilder("item");
			builder.append(size+1);
			//判断手里的物品是不是RPGItem
			if(rpgItems.toRPGItem(item) instanceof RPGItem){
				int id = rpgItems.toRPGItem(item).getID();
				if(!plugin.getItems("setting."+args[1]).contains(id)){
					System.out.println("setting."+args[1]+builder.toString());
					plugin.getConfigUtil().createItem("setting."+args[1]+"."+builder.toString(), id);
					plugin.getItems("setting."+args[1]).add(id);
					plugin.getItemIds("setting."+args[1]).add(builder.toString());
					player.sendMessage(ChatColor.GREEN+"[CustomJob]成功添加一件RPG武器到"+ChatColor.YELLOW+plugin.getConfig().getString("setting.nick."+args[1])+ChatColor.GREEN+"职业，现在共有"+ChatColor.YELLOW+(size+1)+ChatColor.GREEN+"件装备.");
					return true;
				}else{
					player.sendMessage(ChatColor.GREEN+"[CustomJob]该物品已经在这个职业中了，不需要再添加!");
					return true;
				}
			}else{
				player.sendMessage(ChatColor.RED+"[CustomJob]手里请拿着RPGItem的自定义武器才可以执行这个命令!");
				return true;
			}
		}
		
		if(args.length==2&&sender.isOp()&&args[0].equalsIgnoreCase("remove")&&sender instanceof Player){
			Player player = (Player) sender;
			RPGItems rpgItems = new RPGItems();
			ItemStack item = player.getItemInHand();
			if(rpgItems.toRPGItem(item) instanceof RPGItem){
				int id = rpgItems.toRPGItem(item).getID();
				if(plugin.getItems("setting."+args[1]).contains(id)){
					System.out.println("。。。。。。。。。。。。。。");
					plugin.getConfigUtil().removNode("setting."+args[1]+"."+plugin.getItemKey("setting."+args[1], id));
					System.out.println("--------------------");
					plugin.getItems("setting."+args[1]).remove(new Integer(id));
					plugin.getItemIds("setting."+args[1]).remove("setting."+args[1]+"."+plugin.getItemKey("setting."+args[1], id));
					System.out.println("********************");
					player.sendMessage(ChatColor.GREEN+"[CustomJob]成功移除该RPG装备，该职业还有"+plugin.getItems("setting."+args[1]).size()+"件RPG装备");
					return true;
				}else{
					player.sendMessage(ChatColor.RED+"[CustomJob]该职业没有这件武器装备!");
					return true;
				}
			}
		}
		if(args[1].equalsIgnoreCase("haha")){
			System.out.println("执行");
			plugin.instance.getConfig().set("setting.nick.job-1",null);
			plugin.instance.saveConfig();
			return true;
		}
		if(args[1].equalsIgnoreCase("ai")){
			plugin.instance.getConfig().set("setting.nick.job-1", "法师");
			plugin.instance.saveConfig();
			return true;
		}
		return false;
	}

}
