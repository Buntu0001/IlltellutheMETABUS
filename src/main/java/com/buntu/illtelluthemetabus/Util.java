package com.buntu.illtelluthemetabus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Util {
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static ArrayList<String> translate(ArrayList<String> message) {
        ArrayList<String> changeArrayListString = new ArrayList<>(message);
        for (int i = 0; i < changeArrayListString.size(); i++) {
            changeArrayListString.set(i, ChatColor.translateAlternateColorCodes('&', changeArrayListString.get(i)));
        }
        return changeArrayListString;
    }

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('&') + "[0-9A-FK-OR]");

    public static String stripColorCodes(String input) {
        return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static Object[] secondsToMinutes(Integer seconds) {
        Integer buffer = seconds;
        if (buffer >= 60) {
            Integer minutes = buffer / 60;
            buffer -= minutes * 60;
            return new Object[]{minutes, buffer};
        } else {
            return new Object[]{0, buffer};
        }
    }

    public static Plugin plugin;
    public static YamlController yamlController;
}
