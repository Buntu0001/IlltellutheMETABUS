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
    public static Plugin plugin;
    public static void openQuestionGui(Player player, Question question) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, Util.translate("&e" + question.getQuestionTitle()));

        ItemStack questionBook = new ItemStack(Material.ENCHANTED_BOOK); // slot 4
        ItemMeta questionBookMeta = questionBook.getItemMeta();
        questionBookMeta.setDisplayName(Util.translate(question.getQuestionContext()));
        questionBookMeta.setLore(Util.translate(question.getQuestionContextLores()));
        questionBook.setItemMeta(questionBookMeta);

        // slot 11 ~ 15

        ItemStack questionFirstOptions = new ItemStack(4870, 1, (short) 8);
        ItemMeta questionFirstOptionsMeta = questionFirstOptions.getItemMeta();
        questionFirstOptionsMeta.setDisplayName(Util.translate(question.getQuestionOptions().get(0)));
        questionFirstOptions.setItemMeta(questionFirstOptionsMeta);

        ItemStack questionSecondOptions = new ItemStack(4871, 1, (short) 0);
        ItemMeta questionSecondOptionsMeta = questionSecondOptions.getItemMeta();
        questionSecondOptionsMeta.setDisplayName(Util.translate(question.getQuestionOptions().get(1)));
        questionSecondOptions.setItemMeta(questionSecondOptionsMeta);

        ItemStack questionThirdOptions = new ItemStack(4871, 1, (short) 8);
        ItemMeta questionThirdOptionsMeta = questionThirdOptions.getItemMeta();
        questionThirdOptionsMeta.setDisplayName(Util.translate(question.getQuestionOptions().get(2)));
        questionThirdOptions.setItemMeta(questionThirdOptionsMeta);

        ItemStack questionFourthOptions = new ItemStack(4872, 1, (short) 0);
        ItemMeta questionFourthOptionsMeta = questionFourthOptions.getItemMeta();
        questionFourthOptionsMeta.setDisplayName(Util.translate(question.getQuestionOptions().get(3)));
        questionFourthOptions.setItemMeta(questionFourthOptionsMeta);

        ItemStack questionFifthOptions = new ItemStack(4872, 1, (short) 8);
        ItemMeta questionFifthOptionsMeta = questionFifthOptions.getItemMeta();
        questionFifthOptionsMeta.setDisplayName(Util.translate(question.getQuestionOptions().get(4)));
        questionFifthOptions.setItemMeta(questionFifthOptionsMeta);

        inventory.setItem(4, questionBook);
        inventory.setItem(11, questionFifthOptions);
        inventory.setItem(12, questionSecondOptions);
        inventory.setItem(13, questionThirdOptions);
        inventory.setItem(14, questionFourthOptions);
        inventory.setItem(15, questionFifthOptions);

        player.openInventory(inventory);


    }
}
