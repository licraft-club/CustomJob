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
			sender.sendMessage(ChatColor.AQUA+"/custom out  --�������ְҵ");
			sender.sendMessage(ChatColor.AQUA+"/custom help  --�鿴����");
			sender.sendMessage(ChatColor.AQUA+"/custom help admin  --����Ա�˵�");
			sender.sendMessage(ChatColor.YELLOW+"------------------------------------------------------------------");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("help")&&args[1].equalsIgnoreCase("admin")){
			sender.sendMessage(ChatColor.YELLOW+"-----------------.["+ChatColor.RED+this.plugin.getConfig().getString("menu.title")+ChatColor.YELLOW+"].-----------------");
			sender.sendMessage(ChatColor.AQUA+"/cjadmin add [ְҵ]  --��������RPGItem������ĳ��ְҵ");
			sender.sendMessage(ChatColor.AQUA+"/cjadmin remove [ְҵ] --��ĳ��ְҵ���Ƴ������е�RPGItem");
			sender.sendMessage(ChatColor.AQUA+"/cjadmin trans [���] [ְҵ] --��ĳ���תְ��ĳ��ְҵ(���˳�ְҵ)");
			sender.sendMessage(ChatColor.AQUA+"/cjadmin out [���] --��ĳ����˳�����ְҵ");
			sender.sendMessage(ChatColor.AQUA+"/cjadmin reload  --���ز��");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("trans")&&args.length>=2&&sender instanceof Player&&sender.isOp()){
			Player player = (Player)sender;
			String job_name = args[1];
			if(plugin.isJob(job_name)){
				String old_job = plugin.getPlayerConfig().getString(player.getName().toString());
				if(old_job!=null){
					//תְ���ڶ�ְҵ�������˳���ְҵ��ֱ��ת��
					if(job_name.contains("sub")){
						if(job_name.contains(old_job)){
							this.clearBP(player);
							plugin.joinAJob(player.getName().toString(), job_name);
							player.sendMessage(ChatColor.AQUA+"[CustomJob]��ɹ�ת��ְҵ"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+old_job)+ChatColor.AQUA+"����ְҵ"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+job_name));
							return true;
						}else{
							player.sendMessage(ChatColor.RED+"[CustomJob]��ְҵ����������ְҵ����ְҵ�����Բ���ת��");
							return true;
						}
					}
					player.sendMessage(ChatColor.RED+"[CustomJob]����ѡ��ְҵ֮ǰ�������˳�ԭ����ְҵ,ָ��/custom out");
					return true;
//					if(old_job.equalsIgnoreCase(args[1])){
//						player.sendMessage(ChatColor.RED+"[CustomJob]���Ѿ������ְҵ�ˣ���Ҫ�ظ�ѡ��");
//						return true;
//					}
//					//��������Ѿ�����ְҵ��Ҫתְ��ʱ��
//					plugin.transJob(player.getName().toString(),job_name);
//					this.clearBP(player, old_job);
//					player.sendMessage(ChatColor.AQUA+"[CustomJob]תְ�ɹ���ԭ��ְҵ��װ������գ������ڵ�ְҵ��"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+job_name));
//					return true;
				}else{//�������û��ְҵ��ʱ���������תְ����,û�б�Ҫ��ձ���
					//�����û��ְҵ��ʱ����ѡ��ڶ�ְҵ
					if(job_name.contains("sub")){
						player.sendMessage(ChatColor.RED+"[CustomJob]������û��ְҵ�����ܽ���תְ�ڶ�ְҵ�Ĳ���");
						return true;
					}
					plugin.joinAJob(player.getName().toString(),job_name);
					player.sendMessage(ChatColor.AQUA+"[CustomJob]ѡ��ְҵ�ɹ��������ڵ�ְҵ��"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+job_name));
					return true;
				}
			}else{
				sender.sendMessage(ChatColor.RED+"[CustomJob]û�и�ְҵ������������!");
				return true;
			}
		}
		
		if(args[0].equalsIgnoreCase("out")&&sender instanceof Player){
			Player player = (Player)sender;
			if(plugin.getPlayerConfig().contains(player.getName().toString())){
				plugin.clearJob(player.getName().toString());
				this.clearBP(player);
				sender.sendMessage(ChatColor.AQUA+"[CustomJob]�ɹ��˳�ְҵϵͳ���㲻��ӵ���κ�ְҵ");
				return true;
			}else{
				player.sendMessage(ChatColor.RED+"[CustomJob]������û��ְҵ���޷��˳�����ѡ��ְҵ");
				return true;
			}
		}
		sender.sendMessage(ChatColor.RED+"[CustomJob]�������ָ���ʽ���󣬻���û��Ȩ��!");
		return false;
	}
	//�����ұ�����������ְҵװ��
	public void clearBP(Player player){
		ItemStack[] inventoryItems = player.getInventory().getContents();
		//��մ������ϵ�װ��
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
		//��ձ������װ��
		for(ItemStack item : inventoryItems){
			if(item!=null&&plugin.isJobItem(item.getTypeId()))
				player.getInventory().remove(item);
		}
	}

}
