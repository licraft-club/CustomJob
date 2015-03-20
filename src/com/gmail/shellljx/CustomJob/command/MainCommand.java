package com.gmail.shellljx.CustomJob.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
		
		if(args[0].equalsIgnoreCase("trans")&&args.length>=2&&sender instanceof Player){
			Player player = (Player)sender;
			String job_name = args[1];
			if(plugin.isJob(job_name)){
				String old_job = plugin.getPlayerConfig().getString(player.getName().toString());
				if(old_job!=null){
					if(old_job.equalsIgnoreCase(args[1])){
						player.sendMessage(ChatColor.RED+"[CustomJob]你已经是这个职业了，不要重复选择");
						return true;
					}
					//当该玩家已经有了职业，要转职的时候
					plugin.transJob(player.getName().toString(),job_name);
					this.clearBP(player, old_job);
					player.sendMessage(ChatColor.AQUA+"[CustomJob]转职成功，原来职业的装备已清空，你现在的职业是"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+job_name));
					return true;
				}else{//当该玩家没有职业的时候输入这个转职命令,没有必要清空背包
					plugin.joinAJob(player.getName().toString(),job_name);
					player.sendMessage(ChatColor.AQUA+"[CustomJob]选择职业成功，你现在的职业是"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+job_name));
					return true;
				}
			}else{
				sender.sendMessage(ChatColor.RED+"[CustomJob]没有该职业，请重新输入!");
				return true;
			}
		}
		
		if(args[0].equalsIgnoreCase("test")){
			System.out.println("。。。。。。。。。");
			Player player = (Player)sender;
			List<ItemStack> wearItem = new ArrayList<>();
			ItemStack[] items = player.getEquipment().getArmorContents();
			if(player.getEquipment().getBoots()!=null)
				wearItem.add(player.getEquipment().getBoots());
			if(player.getEquipment().getHelmet()!=null)
				wearItem.add(player.getEquipment().getHelmet());
			if(player.getEquipment().getChestplate()!=null)
				wearItem.add(player.getEquipment().getChestplate());
			if(player.getEquipment().getLeggings()!=null)
				wearItem.add(player.getEquipment().getLeggings());
			System.out.println("items"+items.length+"wear"+wearItem.size());
			for(ItemStack item:items){
				if(item!=null)
					System.out.println(item.getType());
			}
			return true;
		}
		
		return false;
	}
	
	public void clearBP(Player player,String old_job){
		ItemStack[] inventoryItems = player.getInventory().getContents();
		//清空穿在身上的装备
		ItemStack boot = player.getEquipment().getBoots();
		ItemStack chestplate = player.getEquipment().getChestplate();
		ItemStack legging = player.getEquipment().getLeggings();
		ItemStack helmet = player.getEquipment().getHelmet();
		if(boot!=null&&plugin.include(old_job, boot))
			player.getInventory().setBoots(null);
		if(chestplate!=null&&plugin.include(old_job, chestplate))
			player.getInventory().setChestplate(null);
		if(legging!=null&&plugin.include(old_job, legging))
			player.getInventory().setLeggings(null);
		if(helmet!=null&&plugin.include(old_job, helmet))
			player.getInventory().setHelmet(null);
		//清空背包里的装备
		for(ItemStack item : inventoryItems){
			if(item!=null&&plugin.include(old_job,item))
				player.getInventory().remove(item);
		}
	}

}
