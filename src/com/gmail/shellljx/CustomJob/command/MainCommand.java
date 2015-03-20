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
		
		if(args[0].equalsIgnoreCase("trans")&&args.length>=2&&sender instanceof Player){
			Player player = (Player)sender;
			String job_name = args[1];
			if(plugin.isJob(job_name)){
				String old_job = plugin.getPlayerConfig().getString(player.getName().toString());
				if(old_job!=null){
					if(old_job.equalsIgnoreCase(args[1])){
						player.sendMessage(ChatColor.RED+"[CustomJob]���Ѿ������ְҵ�ˣ���Ҫ�ظ�ѡ��");
						return true;
					}
					//��������Ѿ�����ְҵ��Ҫתְ��ʱ��
					plugin.transJob(player.getName().toString(),job_name);
					this.clearBP(player, old_job);
					player.sendMessage(ChatColor.AQUA+"[CustomJob]תְ�ɹ���ԭ��ְҵ��װ������գ������ڵ�ְҵ��"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+job_name));
					return true;
				}else{//�������û��ְҵ��ʱ���������תְ����,û�б�Ҫ��ձ���
					plugin.joinAJob(player.getName().toString(),job_name);
					player.sendMessage(ChatColor.AQUA+"[CustomJob]ѡ��ְҵ�ɹ��������ڵ�ְҵ��"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+job_name));
					return true;
				}
			}else{
				sender.sendMessage(ChatColor.RED+"[CustomJob]û�и�ְҵ������������!");
				return true;
			}
		}
		
		if(args[0].equalsIgnoreCase("test")){
			System.out.println("������������������");
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
		//��մ������ϵ�װ��
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
		//��ձ������װ��
		for(ItemStack item : inventoryItems){
			if(item!=null&&plugin.include(old_job,item))
				player.getInventory().remove(item);
		}
	}

}
