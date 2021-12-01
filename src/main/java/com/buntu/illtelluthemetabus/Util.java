package com.buntu.illtelluthemetabus;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

public class Util {
    public static String inventoryTitleSplitter = "                  ";

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
        String bufferString;
        if (buffer >= 60) {
            Integer minutes = buffer / 60;
            buffer -= minutes * 60;
            bufferString = String.valueOf(buffer);
            if (bufferString.length() == 1) {
                bufferString = "0" + bufferString;
            }
            return new Object[]{minutes, bufferString};
        } else {
            bufferString = String.valueOf(buffer);
            if (bufferString.length() == 1) {
                 bufferString = "0" + bufferString;
            }
            return new Object[]{0, bufferString};
        }
    }

    public static Integer determineAnswerNumber(Integer clickedInventorySlot) {
        return clickedInventorySlot - 10;
    }

    public static Plugin plugin;
    public static YamlManager yamlManager;
}
