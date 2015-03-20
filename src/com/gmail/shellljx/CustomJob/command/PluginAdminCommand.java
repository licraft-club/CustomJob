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

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// TODO Auto-generated method stub
		if(args.length==2&&sender.isOp()&&args[0].equalsIgnoreCase("add")&&sender instanceof Player){
			Player player = (Player) sender;
			RPGItems rpgItems = new RPGItems();
			ItemStack item = player.getItemInHand();
			int id = item.getTypeId();
			if(plugin.createItem("setting."+args[1], id)){
				player.sendMessage(ChatColor.GREEN+"[CustomJob]成功添加一件RPG武器到"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+args[1])+ChatColor.GREEN+"职业，现在共有"+ChatColor.YELLOW+plugin.getitems("setting."+args[1]).size()+ChatColor.GREEN+"件装备.");
			}else{
				player.sendMessage(ChatColor.RED+"[CustomJob]该职业已经添加了这个武器，无需再次添加！");
			}
//			System.out.println("Typeid"+item.getTypeId());
			return true;
			//判断手里的物品是不是RPGItem
//			if(rpgItems.toRPGItem(item) instanceof RPGItem){
//				int id = rpgItems.toRPGItem(item).getID();
//					System.out.println("setting."+args[1]+"-"+id);
//					if(plugin.createItem("setting."+args[1], id)){
//						player.sendMessage(ChatColor.GREEN+"[CustomJob]成功添加一件RPG武器到"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+args[1])+ChatColor.GREEN+"职业，现在共有"+ChatColor.YELLOW+plugin.getitems("setting."+args[1]).size()+ChatColor.GREEN+"件装备.");	
//					}else{
//						player.sendMessage(ChatColor.RED+"[CustomJob]该职业已经添加了这个武器，无需再次添加！");
//					}
//					return true;
//			}else{
//				player.sendMessage(ChatColor.RED+"[CustomJob]手里请拿着RPGItem的自定义武器才可以执行这个命令!");
//				return true;
//			}
		}
		
		if(args.length==2&&sender.isOp()&&args[0].equalsIgnoreCase("remove")&&sender instanceof Player){
			Player player = (Player) sender;
			RPGItems rpgItems = new RPGItems();
			ItemStack item = player.getItemInHand();
			int id = item.getTypeId();
			if(plugin.removeItem("setting."+args[1], id)){
				player.sendMessage(ChatColor.GREEN+"[CustomJob]成功移除该RPG装备，该职业还有"+plugin.getitems("setting."+args[1]).size()+"件RPG装备");
			}else{
				player.sendMessage(ChatColor.RED+"[CustomJob]该职业还没有添加这个武器装备，所以无法移除!");
			}
//			System.out.println("Typeid"+item.getTypeId());
			return true;
//			if(rpgItems.toRPGItem(item) instanceof RPGItem){
//				int id = rpgItems.toRPGItem(item).getID();
//					if(plugin.removeItem("setting."+args[1],id)){
//						player.sendMessage(ChatColor.GREEN+"[CustomJob]成功移除该RPG装备，该职业还有"+plugin.getitems("setting."+args[1]).size()+"件RPG装备");
//					}else{
//						player.sendMessage(ChatColor.RED+"[CustomJob]该职业还没有添加这个武器装备，所以无法移除!");
//					}
//					return true;
//			}
		}
		if(args[0].equalsIgnoreCase("test")){
			Player player = (Player)sender;
			System.out.println(player.getItemInHand().getType().name());
			return true;
		}
		return false;
	}

}
