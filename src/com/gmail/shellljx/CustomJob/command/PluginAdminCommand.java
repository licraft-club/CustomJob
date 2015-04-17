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
				sender.sendMessage(ChatColor.RED+"[CustomJob]��������������Ҫ��ӵ�װ��");
				return true;
			}
			if(plugin.createItem("setting."+args[1], id)){
				player.sendMessage(ChatColor.GREEN+"[CustomJob]�ɹ����һ��RPG������"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+args[1])+ChatColor.GREEN+"ְҵ�����ڹ���"+ChatColor.YELLOW+plugin.getitems("setting."+args[1]).size()+ChatColor.GREEN+"��װ��.");
			}else{
				player.sendMessage(ChatColor.RED+"[CustomJob]��ְҵ�Ѿ��������������������ٴ���ӣ�");
			}
//			System.out.println("Typeid"+item.getTypeId());
			return true;
			//�ж��������Ʒ�ǲ���RPGItem
//			if(rpgItems.toRPGItem(item) instanceof RPGItem){
//				int id = rpgItems.toRPGItem(item).getID();
//					System.out.println("setting."+args[1]+"-"+id);
//					if(plugin.createItem("setting."+args[1], id)){
//						player.sendMessage(ChatColor.GREEN+"[CustomJob]�ɹ����һ��RPG������"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+args[1])+ChatColor.GREEN+"ְҵ�����ڹ���"+ChatColor.YELLOW+plugin.getitems("setting."+args[1]).size()+ChatColor.GREEN+"��װ��.");	
//					}else{
//						player.sendMessage(ChatColor.RED+"[CustomJob]��ְҵ�Ѿ��������������������ٴ���ӣ�");
//					}
//					return true;
//			}else{
//				player.sendMessage(ChatColor.RED+"[CustomJob]����������RPGItem���Զ��������ſ���ִ���������!");
//				return true;
//			}
		}
		
		if(args.length==2&&sender.isOp()&&args[0].equalsIgnoreCase("remove")&&sender instanceof Player){
			Player player = (Player) sender;
			RPGItems rpgItems = new RPGItems();
			ItemStack item = player.getItemInHand();
			int id = item.getTypeId();
			if(id==0){
				sender.sendMessage(ChatColor.RED+"[CustomJob]������������Ҫɾ����װ��");
				return true;
			}
			if(plugin.removeItem("setting."+args[1], id)){
				player.sendMessage(ChatColor.GREEN+"[CustomJob]�ɹ��Ƴ���RPGװ������ְҵ����"+plugin.getitems("setting."+args[1]).size()+"��RPGװ��");
			}else{
				player.sendMessage(ChatColor.RED+"[CustomJob]��ְҵ��û������������װ���������޷��Ƴ�!");
			}
//			System.out.println("Typeid"+item.getTypeId());
			return true;
//			if(rpgItems.toRPGItem(item) instanceof RPGItem){
//				int id = rpgItems.toRPGItem(item).getID();
//					if(plugin.removeItem("setting."+args[1],id)){
//						player.sendMessage(ChatColor.GREEN+"[CustomJob]�ɹ��Ƴ���RPGװ������ְҵ����"+plugin.getitems("setting."+args[1]).size()+"��RPGװ��");
//					}else{
//						player.sendMessage(ChatColor.RED+"[CustomJob]��ְҵ��û������������װ���������޷��Ƴ�!");
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
								p.sendMessage(ChatColor.RED+"[CustomJob]����ѡ��ְҵ֮ǰ�������˳�ԭ����ְҵ,ָ��/custom out");
								return true;
							}else{//�������û��ְҵ��ʱ���������תְ����,û�б�Ҫ��ձ���
								plugin.joinAJob(p.getName().toString(),args[2]);
								p.sendMessage(ChatColor.AQUA+"[CustomJob]ѡ��ְҵ�ɹ��������ڵ�ְҵ��"+ChatColor.YELLOW+plugin.getConfig().getString("nick."+args[2]));
								return true;
							}
						}else{
							sender.sendMessage(ChatColor.RED+"[CustomJob]û�и�ְҵ������������!");
							return true;
						}
					}
				}
				System.out.println("[CustomJob]���û������");
			}else{
				sender.sendMessage(ChatColor.RED+"[CustomJob]����̨תְ��������");
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
							p.sendMessage(ChatColor.AQUA+"[CustomJob]�ɹ��˳�ְҵϵͳ���㲻��ӵ���κ�ְҵ");
							return true;
						}else{
							p.sendMessage(ChatColor.RED+"[CustomJob]������û��ְҵ���޷��˳�����ѡ��ְҵ");
							return true;
						}
					}
				}
				System.out.println("[CustomJob]����Ҳ�����");
				return true;
			}else{
				sender.sendMessage("[CustomJob]����̨��������");
				return true;
			}
		}
		else{
			sender.sendMessage(ChatColor.RED+"[CustomJob]�������ָ���ʽ���󣬻���û��Ȩ��!");
			return true;
		}
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
