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
			//����װ������
			int size = plugin.getItems("setting."+args[1]).size();
			StringBuilder builder = new StringBuilder("item");
			builder.append(size+1);
			//�ж��������Ʒ�ǲ���RPGItem
			if(rpgItems.toRPGItem(item) instanceof RPGItem){
				int id = rpgItems.toRPGItem(item).getID();
				if(!plugin.getItems("setting."+args[1]).contains(id)){
					System.out.println("setting."+args[1]+builder.toString());
					plugin.getConfigUtil().createItem("setting."+args[1]+"."+builder.toString(), id);
					plugin.getItems("setting."+args[1]).add(id);
					plugin.getItemIds("setting."+args[1]).add(builder.toString());
					player.sendMessage(ChatColor.GREEN+"[CustomJob]�ɹ����һ��RPG������"+ChatColor.YELLOW+plugin.getConfig().getString("setting.nick."+args[1])+ChatColor.GREEN+"ְҵ�����ڹ���"+ChatColor.YELLOW+(size+1)+ChatColor.GREEN+"��װ��.");
					return true;
				}else{
					player.sendMessage(ChatColor.GREEN+"[CustomJob]����Ʒ�Ѿ������ְҵ���ˣ�����Ҫ�����!");
					return true;
				}
			}else{
				player.sendMessage(ChatColor.RED+"[CustomJob]����������RPGItem���Զ��������ſ���ִ���������!");
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
					System.out.println("����������������������������");
					plugin.getConfigUtil().removNode("setting."+args[1]+"."+plugin.getItemKey("setting."+args[1], id));
					System.out.println("--------------------");
					plugin.getItems("setting."+args[1]).remove(new Integer(id));
					plugin.getItemIds("setting."+args[1]).remove("setting."+args[1]+"."+plugin.getItemKey("setting."+args[1], id));
					System.out.println("********************");
					player.sendMessage(ChatColor.GREEN+"[CustomJob]�ɹ��Ƴ���RPGװ������ְҵ����"+plugin.getItems("setting."+args[1]).size()+"��RPGװ��");
					return true;
				}else{
					player.sendMessage(ChatColor.RED+"[CustomJob]��ְҵû���������װ��!");
					return true;
				}
			}
		}
		if(args[1].equalsIgnoreCase("haha")){
			System.out.println("ִ��");
			plugin.instance.getConfig().set("setting.nick.job-1",null);
			plugin.instance.saveConfig();
			return true;
		}
		if(args[1].equalsIgnoreCase("ai")){
			plugin.instance.getConfig().set("setting.nick.job-1", "��ʦ");
			plugin.instance.saveConfig();
			return true;
		}
		return false;
	}

}
