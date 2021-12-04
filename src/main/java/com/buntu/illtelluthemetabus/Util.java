package com.buntu.illtelluthemetabus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
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

    public static ArrayList<QuestionPlayerState> reverseSequentialSort(ArrayList<QuestionPlayerState> unsortRanking) {
        ArrayList<QuestionPlayerState> sortRank = new ArrayList<>(unsortRanking);
        Integer size = sortRank.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (sortRank.get(i).getPlayerScore() < sortRank.get(j).getPlayerScore()) {
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

    public static String animatedString(String normalString, Integer highlightIndex, ChatColor backHighlightChatColor, ChatColor highlightChatColor) {
        String title = normalString;
        Integer[] spaceIndex = findIndexes(" ", title).toArray(new Integer[0]);
        title = title.replace(" ", "");
        String[] splitString = title.split("");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < splitString.length; i++) {
            if (i == highlightIndex) {
                stringBuilder.append(highlightChatColor).append(splitString[i]);
            } else if (i == highlightIndex - 1) {
                stringBuilder.append(backHighlightChatColor).append(splitString[i]);
            } else {
                stringBuilder.append(ChatColor.WHITE).append(splitString[i]);
            }
        }
        for (Integer index : spaceIndex) {
            stringBuilder.insert(index * 3, " ");
        }
        return stringBuilder.toString();
    }

    public static Plugin plugin;
    public static YamlManager yamlManager;
}
