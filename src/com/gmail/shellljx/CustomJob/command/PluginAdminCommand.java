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
		if(args[0].equalsIgnoreCase("test")){
			Player player = (Player)sender;
			System.out.println(player.getItemInHand().getType().name());
			return true;
		}
		return false;
	}

}
