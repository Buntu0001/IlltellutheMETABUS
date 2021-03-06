package com.buntu.illtelluthemetabus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MakeGui {
  public static Inventory getNormalGui(Inventory inventory, Player player, Question question) {
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
    inventory.setItem(11, questionFirstOptions);
    inventory.setItem(12, questionSecondOptions);
    inventory.setItem(13, questionThirdOptions);
    inventory.setItem(14, questionFourthOptions);
    inventory.setItem(15, questionFifthOptions);

    ItemStack glassPane = Util.getGlassPane(0);
    inventory.setItem(0, glassPane);
    inventory.setItem(1, glassPane);
    inventory.setItem(2, glassPane);
    inventory.setItem(6, glassPane);
    inventory.setItem(7, glassPane);
    inventory.setItem(8, glassPane);
    inventory.setItem(9, glassPane);
    inventory.setItem(17, glassPane);
    for (int i = 18; i <= 26; i++) {
      inventory.setItem(i, glassPane);
    }

    return inventory;
  }

  public static Inventory getCommentaryGui(Inventory inventory, Player player, Question question) {
    ItemStack questionBook = new ItemStack(Material.ENCHANTED_BOOK); // slot 4
    ItemMeta questionBookMeta = questionBook.getItemMeta();
    questionBookMeta.setDisplayName(Util.translate(question.getQuestionContext()));
    questionBookMeta.setLore(Util.translate(question.getQuestionContextLores()));
    questionBook.setItemMeta(questionBookMeta);

    // center inventory slot num 11 12 13 14 15

    ItemStack glassPane = Util.getGlassPane(5);
    inventory.setItem(0, glassPane);
    inventory.setItem(1, glassPane);
    inventory.setItem(2, glassPane);
    inventory.setItem(6, glassPane);
    inventory.setItem(7, glassPane);
    inventory.setItem(8, glassPane);
    inventory.setItem(9, glassPane);
    inventory.setItem(17, glassPane);
    for (int i = 18; i <= 26; i++) {
      inventory.setItem(i, glassPane);
    }

    return inventory;

  }
}
