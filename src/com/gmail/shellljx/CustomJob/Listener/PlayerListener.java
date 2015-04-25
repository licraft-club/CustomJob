package com.gmail.shellljx.CustomJob.Listener;

import java.awt.Font;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import com.gmail.shellljx.CustomJob.CustomJob;
import com.gmail.shellljx.CustomJob.Util.EnglishChatColor;

public class PlayerListener implements Listener {
	
	private CustomJob plugin;
	private int lastPickId=0;
	private int nowPickId = 0;
	public PlayerListener(CustomJob plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInteractEvent(InventoryClickEvent e){
		//System.out.println("值+"+(e.getWhoClicked() instanceof Player)+"--"+e.getCurrentItem().getType()+"--"+e.getCurrentItem().getType().getId());
		Player player;
		Inventory inv = e.getInventory();
		if(e.getWhoClicked() instanceof Player){
			player = (Player) e.getWhoClicked();
			if(e.getCurrentItem()==null){
				e.setCancelled(true);
				return ;
			}
			//新增附加功能--判断是否在铁毡下
			if(inv instanceof AnvilInventory){
				InventoryView view = e.getView();
				int rawSlot = e.getRawSlot();
				if(view.convertSlot(rawSlot)==rawSlot){
					if(rawSlot==2&&view.getItem(0).getType()==Material.ENCHANTED_BOOK&&view.getItem(1).getType()==Material.ENCHANTED_BOOK){
						e.setCancelled(true);
						e.getWhoClicked().closeInventory();
						((Player)e.getWhoClicked()).sendMessage(ChatColor.RED+"[Customjob]不能相同附魔书附魔");
//						Map<Enchantment,Integer> map1=((EnchantmentStorageMeta)view.getItem(0).getItemMeta()).getStoredEnchants();
//						Map<Enchantment,Integer> map2 = ((EnchantmentStorageMeta)view.getItem(1).getItemMeta()).getStoredEnchants();
//						if(map1.size()==1&&map2.size()==1){
//							String en1="",en2="";
//							for(Enchantment enc:map1.keySet())
//								en1 = enc.getName();
//							for(Enchantment enc:map2.keySet())
//								en2 = enc.getName();
//							System.out.println(en1+"="+en2);
//							if(en1==en2){
//								e.setCancelled(true);
//								e.getWhoClicked().closeInventory();
//								((Player)e.getWhoClicked()).sendMessage(ChatColor.RED+"[Customjob]不能相同附魔书附魔");
//							}
//						}
					}
				}
				}
			//customjob本来的业务逻辑
			int id = e.getCurrentItem().getTypeId();
			if(!plugin.getPlayerConfig().contains(player.getName().toString())){
				if(plugin.isJobItem(id)&&!player.isOp()){
					e.setCancelled(true);
					player.closeInventory();
					player.sendMessage(ChatColor.RED+"[CustomJob]你不能使用该自定义职业的装备!请选择职业或者转职");
				}
				return;
			}
			boolean is = plugin.getitems("setting."+plugin.getPlayerConfig().getString(player.getName().toString())).contains(id);
			if(!is&&plugin.isJobItem(id)&&!player.isOp()){
				e.setCancelled(true);
				player.closeInventory();
				player.sendMessage(ChatColor.RED+"[CustomJob]你不能使用该自定义职业的装备!请选择职业或者转职");
			}
		}
	}
	
	@EventHandler
	public void onPickUpEvent(PlayerPickupItemEvent e){
		Player player = (Player)e.getPlayer();
		int id = e.getItem().getItemStack().getTypeId();
		if(!plugin.getPlayerConfig().contains(player.getName().toString())){
			if(plugin.isJobItem(id)){
				e.setCancelled(true);
				nowPickId=id;
			}
			if(lastPickId!=nowPickId){
				player.sendMessage(ChatColor.RED+"[CustomJob]你不能拾取该自定义职业的装备!请选择职业或者转职");
				lastPickId=id;
			}
			return;
		}
		boolean is = plugin.getitems("setting."+plugin.getPlayerConfig().getString(player.getName().toString())).contains(id);
		if(!is&&plugin.isJobItem(id)&&!player.isOp()){
			e.setCancelled(true);
			nowPickId=id;
			if(lastPickId!=nowPickId){
				player.sendMessage(ChatColor.RED+"[CustomJob]你不能拾取该自定义职业的装备!请选择职业或者转职");
				lastPickId=id;
			}
		}
	}
	
	@EventHandler
	public void onInteractListener(PlayerInteractEvent e){
		Player player = e.getPlayer();
		Action a = e.getAction();
		if(!e.hasItem())
			return;
		int id = e.getItem().getTypeId();
		if(!plugin.getPlayerConfig().contains(player.getName().toString())){
			if(plugin.isJobItem(id)){
				e.setCancelled(true);
				player.sendMessage(ChatColor.RED+"[CustomJob]你不能使用该自定义职业的装备!请选择职业或者转职");
			}
			return;
		}
		if((a==Action.LEFT_CLICK_AIR||a==Action.LEFT_CLICK_BLOCK||a==Action.RIGHT_CLICK_AIR||a==Action.RIGHT_CLICK_BLOCK)){
			boolean is=plugin.getitems("setting."+plugin.getPlayerConfig().getString(player.getName().toString())).contains(id);
			if(!is&&plugin.isJobItem(id)&&!player.isOp()){
				e.setCancelled(true);
				player.sendMessage(ChatColor.RED+"[CustomJob]你不能使用该自定义职业的装备!请选择职业或者转职");
			}
		}
	}
	
	@EventHandler
	public void onDamageListener(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			Player player = (Player)e.getDamager();
			ItemStack item = player.getItemInHand();
			if(item==null)
				return;
			int id = item.getTypeId();
			if(!plugin.getPlayerConfig().contains(player.getName().toString())){
				if(plugin.isJobItem(id)){
					e.setCancelled(true);
					player.sendMessage(ChatColor.RED+"[CustomJob]你不能使用该自定义职业的装备造成伤害，请选择职业或者转职!");
				}
				return;
			}
			//System.out.println(player.getName().toString()+id);
			boolean is = plugin.getitems("setting."+plugin.getPlayerConfig().getString(player.getName().toString())).contains(id);
			if(!is&&plugin.isJobItem(id)&&!player.isOp()){
				e.setCancelled(true);
				player.sendMessage(ChatColor.RED+"[CustomJob]你不能使用该自定义职业的装备造成伤害，请选择职业或者转职!");
			}
		}
	}
	
	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent e){
		Player player = e.getPlayer();
		String jobname = plugin.getPlayerConfig().getString(player.getName().toString());
		if(jobname!=null){
			String format = e.getFormat();
			ChatColor c = EnglishChatColor.fromString(plugin.getConfig().getString("color."+jobname)).getColor();
			String prefix = plugin.getConfig().getString("nick."+jobname);
			e.setFormat(c+"["+prefix+"]");
			String pformat = e.getFormat();
			//System.out.println(format);
			//System.out.println(pformat);
			e.setFormat(pformat+format);
			e.setFormat(e.getFormat());
		}
	}
	
}
