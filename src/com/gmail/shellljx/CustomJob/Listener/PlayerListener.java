package com.gmail.shellljx.CustomJob.Listener;

import java.awt.Font;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

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
		if(e.getWhoClicked() instanceof Player){

			player = (Player) e.getWhoClicked();
			if(e.getCurrentItem()==null){
				e.setCancelled(true);
				return ;
			}
			int id = e.getCurrentItem().getTypeId();
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
		if(e.hasItem()&&(a==Action.LEFT_CLICK_AIR||a==Action.LEFT_CLICK_BLOCK||a==Action.RIGHT_CLICK_AIR||a==Action.RIGHT_CLICK_BLOCK)){
			int id = e.getItem().getTypeId();
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
			System.out.println(player.getName().toString()+id);
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
			e.setFormat(pformat+format);
			e.setFormat(Font.BOLD+e.getFormat());
		}
	}
	
}
