package com.gmail.shellljx.CustomJob.command;

import java.io.Console;

import org.bukkit.Bukkit;
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
			if(id==0){
				sender.sendMessage(ChatColor.RED+"[CustomJob]请在手上拿着你要添加的装备");
				return true;
			}
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
			if(id==0){
				sender.sendMessage(ChatColor.RED+"[CustomJob]请在手上拿着要删除的装备");
				return true;
			}
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
		
		if(args[0].equalsIgnoreCase("reload")&&sender.isOp()){
			plugin.reloadConfig();
			sender.sendMessage(ChatColor.AQUA+"[CustomJob]config.yml reload");
			plugin.playerConfigReload();
			sender.sendMessage(ChatColor.AQUA+"[CustomJob]save.yml reload");
			return true;
		}
		if(args[0].equalsIgnoreCase("trans")){
			if(args.length==3&&sender.isOp()){
				for(Player p:Bukkit.getServer().getOnlinePlayers()){
					if(p.getName().equalsIgnoreCase(args[1])){
						if(plugin.isJob(args[2])){
							String old_job = plugin.getPlayerConfig().getString(p.getName().toString());
							if(old_job!=null){
								p.sendMessage(ChatColor.RED+"[CustomJob]重新选择职业之前，请先退出原来的职业,指令/custom out");
								return true;
							}else{//当该玩家没有职业的时候输入这个转职命令,没有必要清空背包
								plugin.joinAJob(p.getName().toString(),args[2]);
								p.sendMessage(ChatColor.AQUA+"[CustomJob]选择职业成功，你现在的职业是"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+args[2]));
								return true;
							}
						}else{
							sender.sendMessage(ChatColor.RED+"[CustomJob]没有该职业，请重新输入!");
							return true;
						}
					}
				}
				System.out.println("[CustomJob]玩家没有在线");
			}else{
				sender.sendMessage(ChatColor.RED+"[CustomJob]控制台转职参数错误");
			}
			return true;
		}
		if(args[0].equalsIgnoreCase("out")&&sender.isOp()){
			if(args.length==2&&sender.isOp()){
				for(Player p:Bukkit.getServer().getOnlinePlayers()){
					if(p.getName().equalsIgnoreCase(args[1])){
						if(plugin.getPlayerConfig().contains(p.getName().toString())){
							plugin.clearJob(p.getName().toString());
							this.clearBP(p);
							p.sendMessage(ChatColor.AQUA+"[CustomJob]成功退出职业系统，你不再拥有任何职业");
							return true;
						}else{
							p.sendMessage(ChatColor.RED+"[CustomJob]你现在没有职业，无法退出，请选择职业");
							return true;
						}
					}
				}
				System.out.println("[CustomJob]该玩家不在线");
				return true;
			}else{
				sender.sendMessage("[CustomJob]控制台参数错误");
				return true;
			}
		}
		else{
			sender.sendMessage(ChatColor.RED+"[CustomJob]你输入的指令格式错误，或者没有权限!");
			return true;
		}
	}
	
	//清空玩家背包里面所有职业装备
	public void clearBP(Player player){
		ItemStack[] inventoryItems = player.getInventory().getContents();
		//清空穿在身上的装备
		ItemStack boot = player.getEquipment().getBoots();
		ItemStack chestplate = player.getEquipment().getChestplate();
		ItemStack legging = player.getEquipment().getLeggings();
		ItemStack helmet = player.getEquipment().getHelmet();
		if(boot!=null&&plugin.isJobItem(boot.getTypeId()))
			player.getInventory().setBoots(null);
		if(chestplate!=null&&plugin.isJobItem(chestplate.getTypeId()))
			player.getInventory().setChestplate(null);
		if(legging!=null&&plugin.isJobItem(legging.getTypeId()))
			player.getInventory().setLeggings(null);
		if(helmet!=null&&plugin.isJobItem(helmet.getTypeId()))
			player.getInventory().setHelmet(null);
		//清空背包里的装备
		for(ItemStack item : inventoryItems){
			if(item!=null&&plugin.isJobItem(item.getTypeId()))
				player.getInventory().remove(item);
		}
	}

}
