package com.buntu.illtelluthemetabus;

import org.bukkit.ChatColor;

public class Util {
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
