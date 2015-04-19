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
			sender.sendMessage(ChatColor.AQUA+"/custom out  --放弃你的职业");
			sender.sendMessage(ChatColor.AQUA+"/custom help  --查看帮助");
			sender.sendMessage(ChatColor.AQUA+"/custom help admin  --管理员菜单");
			sender.sendMessage(ChatColor.YELLOW+"------------------------------------------------------------------");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("help")&&args[1].equalsIgnoreCase("admin")){
			sender.sendMessage(ChatColor.YELLOW+"-----------------.["+ChatColor.RED+this.plugin.getConfig().getString("menu.title")+ChatColor.YELLOW+"].-----------------");
			sender.sendMessage(ChatColor.AQUA+"/cjadmin add [职业]  --添加手里的RPGItem武器到某个职业");
			sender.sendMessage(ChatColor.AQUA+"/cjadmin remove [职业] --从某个职业中移除你手中的RPGItem");
			sender.sendMessage(ChatColor.AQUA+"/cjadmin trans [玩家] [职业] --将某玩家转职到某个职业(先退出职业)");
			sender.sendMessage(ChatColor.AQUA+"/cjadmin out [玩家] --将某玩家退出所有职业");
			sender.sendMessage(ChatColor.AQUA+"/cjadmin reload  --重载插件");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("trans")&&args.length>=2&&sender instanceof Player&&sender.isOp()){
			Player player = (Player)sender;
			String job_name = args[1];
			if(plugin.isJob(job_name)){
				String old_job = plugin.getPlayerConfig().getString(player.getName().toString());
				if(old_job!=null){
					//转职到第二职业，不用退出父职业，直接转入
					if(job_name.contains("sub")){
						if(job_name.contains(old_job)){
							this.clearBP(player);
							plugin.joinAJob(player.getName().toString(), job_name);
							player.sendMessage(ChatColor.AQUA+"[CustomJob]你成功转入职业"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+old_job)+ChatColor.AQUA+"的子职业"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+job_name));
							return true;
						}else{
							player.sendMessage(ChatColor.RED+"[CustomJob]该职业不是你现在职业的子职业，所以不能转入");
							return true;
						}
					}
					player.sendMessage(ChatColor.RED+"[CustomJob]重新选择职业之前，请先退出原来的职业,指令/custom out");
					return true;
//					if(old_job.equalsIgnoreCase(args[1])){
//						player.sendMessage(ChatColor.RED+"[CustomJob]你已经是这个职业了，不要重复选择");
//						return true;
//					}
//					//当该玩家已经有了职业，要转职的时候
//					plugin.transJob(player.getName().toString(),job_name);
//					this.clearBP(player, old_job);
//					player.sendMessage(ChatColor.AQUA+"[CustomJob]转职成功，原来职业的装备已清空，你现在的职业是"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+job_name));
//					return true;
				}else{//当该玩家没有职业的时候输入这个转职命令,没有必要清空背包
					//当玩家没有职业的时候不能选择第二职业
					if(job_name.contains("sub")){
						player.sendMessage(ChatColor.RED+"[CustomJob]你现在没有职业，不能进行转职第二职业的操作");
						return true;
					}
					plugin.joinAJob(player.getName().toString(),job_name);
					player.sendMessage(ChatColor.AQUA+"[CustomJob]选择职业成功，你现在的职业是"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+job_name));
					return true;
				}
			}else{
				sender.sendMessage(ChatColor.RED+"[CustomJob]没有该职业，请重新输入!");
				return true;
			}
		}
		
		if(args[0].equalsIgnoreCase("out")&&sender instanceof Player){
			Player player = (Player)sender;
			if(plugin.getPlayerConfig().contains(player.getName().toString())){
				plugin.clearJob(player.getName().toString());
				this.clearBP(player);
				sender.sendMessage(ChatColor.AQUA+"[CustomJob]成功退出职业系统，你不再拥有任何职业");
				return true;
			}else{
				player.sendMessage(ChatColor.RED+"[CustomJob]你现在没有职业，无法退出，请选择职业");
				return true;
			}
		}
		sender.sendMessage(ChatColor.RED+"[CustomJob]你输入的指令格式错误，或者没有权限!");
		return false;
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
