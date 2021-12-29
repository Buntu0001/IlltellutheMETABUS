package com.buntu.illtelluthemetabus;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Util {
    public static String inventoryTitleSplitter = "                  ";

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public enum GUI_TYPE {
        NORMAL,
        END_CORRECT,
        END_INCORRECT,
        END
    };

    public static Boolean questionAvailable = false;

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

    public static Object[] secondsToMinutes(Long seconds) {
        Long milliBuffer = seconds - System.currentTimeMillis();
        Integer buffer = milliBuffer.intValue() / 1000;
        String bufferString;
        if (buffer >= 60) {
            Integer minutes = buffer / 60;
            buffer -= minutes * 60;
            bufferString = String.valueOf(buffer);
            if (bufferString.length() == 1) {
                bufferString = "0" + bufferString;
            }
            return new Object[] { minutes, bufferString };
        } else {
            bufferString = String.valueOf(buffer);
            if (bufferString.length() == 1) {
                bufferString = "0" + bufferString;
            }
            return new Object[] { 0, bufferString };
        }
    }

    public static Integer checkAnswer(Integer clickedInventorySlot) {
        return clickedInventorySlot - 10;
    }

    public static ArrayList<QuestionPlayerState> reverseSequentialSort(ArrayList<QuestionPlayerState> unsortRanking) {
        ArrayList<QuestionPlayerState> sortRank = new ArrayList<>(unsortRanking);
        Integer size = sortRank.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (sortRank.get(i).getScore() < sortRank.get(j).getScore()) {
                    QuestionPlayerState tempQuestionPlayerState = sortRank.get(i);
                    sortRank.set(i, sortRank.get(j));
                    sortRank.set(j, tempQuestionPlayerState);
                }
            }
        }
        return sortRank;
    }

    public static List<Integer> findIndexes(String word, String document) {
        List<Integer> indexList = new ArrayList<Integer>();
        int index = document.indexOf(word);
        while (index != -1) {
            indexList.add(index);
            index = document.indexOf(word, index + word.length());
        }
        return indexList;
    }

    public static String animatedString(String normalString, Integer index) {
        index -= 1;
        String[] split = normalString.split("");
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.WHITE);
        for (int i = 0; i < index - 2; i++) {
            builder.append(split[i]);
        }
        for (int i = index - 2; i <= index; i++) {
            if (!(i >= split.length) && (i >= 0)) {
                builder.append(ChatColor.GOLD).append(split[i]);
            }
        }
        builder.append(ChatColor.WHITE);
        for (int i = index + 1; i < split.length; i++) {
            builder.append(split[i]);
        }
        return builder.toString();
    }

    private static ItemStack normalGlassPane = new ItemStack(160, 1, (short) 0);
    private static ItemStack correctGlassPane = new ItemStack(160, 1, (short) 5);
    private static ItemStack wrongGlassPane = new ItemStack(160, 1, (short) 14);

    public static void initaillizeGlassPane() {
        ItemMeta normalGlassPaneMeta = normalGlassPane.getItemMeta();
        ItemMeta correctGlassPaneMeta = correctGlassPane.getItemMeta();
        ItemMeta wrongGlassPaneMeta = wrongGlassPane.getItemMeta();

        normalGlassPaneMeta.setDisplayName(Util.translate("&a잘 생각 해보면 답이 생각날지도?"));
        correctGlassPaneMeta.setDisplayName(Util.translate("&a정답!"));
        wrongGlassPaneMeta.setDisplayName(Util.translate("&c다시 한번 생각해 봐요!"));

        normalGlassPane.setItemMeta(normalGlassPaneMeta);
        correctGlassPane.setItemMeta(correctGlassPaneMeta);
        wrongGlassPane.setItemMeta(wrongGlassPaneMeta);
    }

    public static ItemStack getGlassPane(Integer dataType) {
        switch (dataType) {
            case 0:
                return normalGlassPane;
            case 5:
                return correctGlassPane;
            case 14:
                return wrongGlassPane;
            default:
                return new ItemStack(160, 1, (short) 0);
        }
    }

    public static GUI_TYPE checkGUIType(Inventory inventory) {
        ItemStack glasspane = inventory.getItem(0);
        if (glasspane.getData() != new MaterialData(160, (byte) 0)) {
            return GUI_TYPE.END;
        } else {
            return GUI_TYPE.NORMAL;
        }
    }

    public static Plugin plugin;
    public static YamlManager yamlManager;
}
