package com.gmail.shellljx.CustomJob.Util;

import org.bukkit.ChatColor;

public enum EnglishChatColor {
	AQUA("AQUA",ChatColor.AQUA),
	BLACK("BLACE",ChatColor.BLACK),
	BLUE("BLUE",ChatColor.BLUE),
	DARKAQUA("DARKAQUA",ChatColor.DARK_AQUA),
	DARKBLUE("DARKABLUE",ChatColor.DARK_BLUE),
	DARKGRAY("DARKGRAY",ChatColor.DARK_GRAY),
	DARKGREEN("DARKGREEN",ChatColor.DARK_GREEN),
	DARKPURPLE("DARKPURPLE",ChatColor.DARK_PURPLE),
	DARKRED("DARKRED",ChatColor.DARK_RED),
	GOLD("GOLD",ChatColor.GOLD),
	GRAY("GRAY",ChatColor.GRAY),
	GREEN("GREEN",ChatColor.GREEN),
	LIGHTPURULE("LIGHTPURPLE",ChatColor.LIGHT_PURPLE),
	RED("RED",ChatColor.RED),
	YELLOW("YELLOW",ChatColor.YELLOW),
	WHITE("WHITE",ChatColor.WHITE);
	
	private ChatColor color;
	private String text;
	
	private EnglishChatColor(String name,ChatColor color){
		this.color=color;
		this.text=name;
	}
	public String getText(){
		return this.text;
	}

	public ChatColor getColor(){
		return this.color;
	}
	public static EnglishChatColor fromString(String text){
		if(text !=null){
			for(EnglishChatColor c :values()){
				if(text.equalsIgnoreCase(c.text)){
					return c;
				}
			}
		}
		return null;
	}

}