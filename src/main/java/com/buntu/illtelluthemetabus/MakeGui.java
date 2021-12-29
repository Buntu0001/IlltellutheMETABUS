package com.buntu.illtelluthemetabus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MakeGui {
    public static Inventory getNormalGUI(Inventory inventory, Player player, Question question) {
        ItemStack questionBook = new ItemStack(Material.ENCHANTED_BOOK); // slot 4
        ItemMeta questionBookMeta = questionBook.getItemMeta();
        questionBookMeta.setDisplayName(Util.translate("&3문제"));

        String[] splitContext = question.getContext().split("/n/");
        ArrayList<String> context = new ArrayList<>();
        for (int i = 0; i < splitContext.length; i++) {
            context.add(Util.translate(splitContext[i]));
        }
        if (question.getContextLores().size() > 0) {
            context.add("");
            context.addAll(Util.translate(question.getContextLores()));
        }
        questionBookMeta.setLore(Util.translate(context));
        questionBook.setItemMeta(questionBookMeta);

        ItemStack netherStar = new ItemStack(Material.NETHER_STAR);
        ItemMeta netherStarMeta = netherStar.getItemMeta();
        netherStarMeta.setDisplayName(Util.translate("&e문제 정보"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Util.translate("&7문제 난이도: " + question.getDifficulty()));
        lore.add(Util.translate("&7점수: &b" + question.getScore()));
        netherStarMeta.setLore(lore);
        netherStar.setItemMeta(netherStarMeta);

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName(Util.translate("&c포기하기"));
        lore.clear();
        lore.add(Util.translate("&7클릭 시 문제를 포기합니다."));
        barrierMeta.setLore(lore);
        barrier.setItemMeta(barrierMeta);

        // slot 11 ~ 15

        ItemStack questionFirstOptions = new ItemStack(4870, 1, (short) 8);
        ItemMeta questionFirstOptionsMeta = questionFirstOptions.getItemMeta();
        questionFirstOptionsMeta.setDisplayName(Util.translate("&f① &e" + question.getOptions().get(0)));
        ArrayList<String> optionLore = new ArrayList<>();
        optionLore.add(Util.translate("&7클릭 시 &61번&7을 선택합니다."));
        questionFirstOptionsMeta.setLore(optionLore);
        questionFirstOptions.setItemMeta(questionFirstOptionsMeta);

        ItemStack questionSecondOptions = new ItemStack(4871, 1, (short) 0);
        ItemMeta questionSecondOptionsMeta = questionSecondOptions.getItemMeta();
        questionSecondOptionsMeta.setDisplayName(Util.translate("&f② &e" + question.getOptions().get(1)));
        optionLore.clear();
        optionLore.add(Util.translate("&7클릭 시 &62번&7을 선택합니다."));
        questionSecondOptionsMeta.setLore(optionLore);
        questionSecondOptions.setItemMeta(questionSecondOptionsMeta);

        ItemStack questionThirdOptions = new ItemStack(4871, 1, (short) 8);
        ItemMeta questionThirdOptionsMeta = questionThirdOptions.getItemMeta();
        questionThirdOptionsMeta.setDisplayName(Util.translate("&f③ &e" + question.getOptions().get(2)));
        optionLore.clear();
        optionLore.add(Util.translate("&7클릭 시 &63번&7을 선택합니다."));
        questionThirdOptionsMeta.setLore(optionLore);
        questionThirdOptions.setItemMeta(questionThirdOptionsMeta);

        ItemStack questionFourthOptions = new ItemStack(4872, 1, (short) 0);
        ItemMeta questionFourthOptionsMeta = questionFourthOptions.getItemMeta();
        questionFourthOptionsMeta.setDisplayName(Util.translate("&f④ &e" + question.getOptions().get(3)));
        optionLore.clear();
        optionLore.add(Util.translate("&7클릭 시 &64번&7을 선택합니다."));
        questionFourthOptionsMeta.setLore(optionLore);
        questionFourthOptions.setItemMeta(questionFourthOptionsMeta);

        ItemStack questionFifthOptions = new ItemStack(4872, 1, (short) 8);
        ItemMeta questionFifthOptionsMeta = questionFifthOptions.getItemMeta();
        questionFifthOptionsMeta.setDisplayName(Util.translate("&f⑤ &e" + question.getOptions().get(4)));
        optionLore.clear();
        optionLore.add(Util.translate("&7클릭 시 &65번&7을 선택합니다."));
        questionFifthOptionsMeta.setLore(optionLore);
        questionFifthOptions.setItemMeta(questionFifthOptionsMeta);

        inventory.setItem(4, questionBook);
        inventory.setItem(3, netherStar);
        inventory.setItem(5, barrier);
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

    public static Inventory getCommentaryGUI(Inventory inventory, Player player, Question question, Boolean isCorrect) {
        ItemStack questionBook = new ItemStack(Material.ENCHANTED_BOOK); // slot 4
        ItemMeta questionBookMeta = questionBook.getItemMeta();
        questionBookMeta.setDisplayName(Util.translate(question.getContext()));
        questionBookMeta.setLore(Util.translate(question.getContextLores()));
        questionBook.setItemMeta(questionBookMeta);

        ItemStack netherStar = new ItemStack(Material.NETHER_STAR);
        ItemMeta netherStarMeta = netherStar.getItemMeta();
        netherStarMeta.setDisplayName(Util.translate("&e문제 정보"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Util.translate("&7문제 난이도: " + question.getDifficulty()));
        lore.add(Util.translate("&7점수: &b" + question.getScore()));
        netherStarMeta.setLore(lore);
        netherStar.setItemMeta(netherStarMeta);

        ItemStack sunFlower = new ItemStack(Material.DOUBLE_PLANT);
        ItemMeta sunFlowerMeta = sunFlower.getItemMeta();
        sunFlowerMeta.setDisplayName(Util.translate("&6정답 여부"));
        if (isCorrect) {
            lore.clear();
            lore.add(Util.translate(String.format("&a정답&f을 맞히어, &c%d점&f을 획득했습니다.", question.getScore())));
            sunFlowerMeta.setLore(lore);
            sunFlower.setItemMeta(sunFlowerMeta);
        } else {
            lore.clear();
            lore.add(Util.translate("&a정답&f을 맞히지 못하여, &c0점&f을 획득했습니다."));
            sunFlowerMeta.setLore(lore);
            sunFlower.setItemMeta(sunFlowerMeta);
        }

        ItemStack questionFirstOptions = new ItemStack(4870, 1, (short) 8);
        ItemMeta questionFirstOptionsMeta = questionFirstOptions.getItemMeta();
        questionFirstOptionsMeta.setDisplayName(Util.translate("&f① &e" + question.getOptions().get(0)));
        ArrayList<String> optionLore = new ArrayList<>();
        optionLore.add(Util.translate("&7클릭 시 &61번&7을 선택합니다."));
        questionFirstOptionsMeta.setLore(optionLore);
        questionFirstOptions.setItemMeta(questionFirstOptionsMeta);

        ItemStack questionSecondOptions = new ItemStack(4871, 1, (short) 0);
        ItemMeta questionSecondOptionsMeta = questionSecondOptions.getItemMeta();
        questionSecondOptionsMeta.setDisplayName(Util.translate("&f② &e" + question.getOptions().get(1)));
        optionLore.clear();
        optionLore.add(Util.translate("&7클릭 시 &62번&7을 선택합니다."));
        questionSecondOptionsMeta.setLore(optionLore);
        questionSecondOptions.setItemMeta(questionSecondOptionsMeta);

        ItemStack questionThirdOptions = new ItemStack(4871, 1, (short) 8);
        ItemMeta questionThirdOptionsMeta = questionThirdOptions.getItemMeta();
        questionThirdOptionsMeta.setDisplayName(Util.translate("&f③ &e" + question.getOptions().get(2)));
        optionLore.clear();
        optionLore.add(Util.translate("&7클릭 시 &63번&7을 선택합니다."));
        questionThirdOptionsMeta.setLore(optionLore);
        questionThirdOptions.setItemMeta(questionThirdOptionsMeta);

        ItemStack questionFourthOptions = new ItemStack(4872, 1, (short) 0);
        ItemMeta questionFourthOptionsMeta = questionFourthOptions.getItemMeta();
        questionFourthOptionsMeta.setDisplayName(Util.translate("&f④ &e" + question.getOptions().get(3)));
        optionLore.clear();
        optionLore.add(Util.translate("&7클릭 시 &64번&7을 선택합니다."));
        questionFourthOptionsMeta.setLore(optionLore);
        questionFourthOptions.setItemMeta(questionFourthOptionsMeta);

        ItemStack questionFifthOptions = new ItemStack(4872, 1, (short) 8);
        ItemMeta questionFifthOptionsMeta = questionFifthOptions.getItemMeta();
        questionFifthOptionsMeta.setDisplayName(Util.translate("&f⑤ &e" + question.getOptions().get(4)));
        optionLore.clear();
        optionLore.add(Util.translate("&7클릭 시 &65번&7을 선택합니다."));
        questionFifthOptionsMeta.setLore(optionLore);
        questionFifthOptions.setItemMeta(questionFifthOptionsMeta);

        ArrayList<String> ls = new ArrayList<>();
        ls.add(Util.translate("&7&o(해설 없음)"));

        ItemStack correctFirstOptions = new ItemStack(4890, 1, (short) 8);
        ItemMeta correctFirstOptionsMeta = correctFirstOptions.getItemMeta();
        correctFirstOptionsMeta.setDisplayName(Util.translate("&f① &e" + question.getOptions().get(0)));
        if (question.getCommentary().size() > 0) {
            correctFirstOptionsMeta.setLore(Util.translate(question.getCommentary()));
        } else {
            correctFirstOptionsMeta.setLore(Util.translate(ls));
        }
        correctFirstOptions.setItemMeta(correctFirstOptionsMeta);

        ItemStack correctSecondOptions = new ItemStack(4891, 1, (short) 0);
        ItemMeta correctSecondOptionsMeta = correctSecondOptions.getItemMeta();
        correctSecondOptionsMeta.setDisplayName(Util.translate("&f② &e" + question.getOptions().get(1)));
        if (question.getCommentary().size() > 0) {
            correctSecondOptionsMeta.setLore(Util.translate(question.getCommentary()));
        } else {
            correctSecondOptionsMeta.setLore(Util.translate(ls));
        }
        correctSecondOptions.setItemMeta(correctSecondOptionsMeta);

        ItemStack correctThirdOptions = new ItemStack(4891, 1, (short) 8);
        ItemMeta correctThirdOptionsMeta = correctThirdOptions.getItemMeta();
        correctThirdOptionsMeta.setDisplayName(Util.translate("&f③ &e" + question.getOptions().get(2)));
        if (question.getCommentary().size() > 0) {
            correctThirdOptionsMeta.setLore(Util.translate(question.getCommentary()));
        } else {
            correctThirdOptionsMeta.setLore(Util.translate(ls));
        }
        correctThirdOptions.setItemMeta(correctThirdOptionsMeta);

        ItemStack correctFourthOptions = new ItemStack(4892, 1, (short) 0);
        ItemMeta correctFourthOptionsMeta = correctFourthOptions.getItemMeta();
        correctFourthOptionsMeta.setDisplayName(Util.translate("&f④ &e" + question.getOptions().get(3)));
        if (question.getCommentary().size() > 0) {
            correctFourthOptionsMeta.setLore(Util.translate(question.getCommentary()));
        } else {
            correctFourthOptionsMeta.setLore(Util.translate(ls));
        }
        correctFourthOptions.setItemMeta(correctFourthOptionsMeta);

        ItemStack correctFifthOptions = new ItemStack(4892, 1, (short) 8);
        ItemMeta correctFifthOptionsMeta = correctFifthOptions.getItemMeta();
        correctFifthOptionsMeta.setDisplayName(Util.translate("&f⑤ &e" + question.getOptions().get(4)));
        if (question.getCommentary().size() > 0) {
            correctFifthOptionsMeta.setLore(Util.translate(question.getCommentary()));
        } else {
            correctFifthOptionsMeta.setLore(Util.translate(ls));
        }
        correctFifthOptions.setItemMeta(correctFifthOptionsMeta);

        switch (question.getAnswer()) {
            case 1:
                inventory.setItem(11, correctFirstOptions);
                inventory.setItem(12, questionSecondOptions);
                inventory.setItem(13, questionThirdOptions);
                inventory.setItem(14, questionFourthOptions);
                inventory.setItem(15, questionFifthOptions);
                break;
            case 2:
                inventory.setItem(11, questionFirstOptions);
                inventory.setItem(12, correctSecondOptions);
                inventory.setItem(13, questionThirdOptions);
                inventory.setItem(14, questionFourthOptions);
                inventory.setItem(15, questionFifthOptions);
                break;
            case 3:
                inventory.setItem(11, questionFirstOptions);
                inventory.setItem(12, questionSecondOptions);
                inventory.setItem(13, correctThirdOptions);
                inventory.setItem(14, questionFourthOptions);
                inventory.setItem(15, questionFifthOptions);
                break;
            case 4:
                inventory.setItem(11, questionFirstOptions);
                inventory.setItem(12, questionSecondOptions);
                inventory.setItem(13, questionThirdOptions);
                inventory.setItem(14, correctFourthOptions);
                inventory.setItem(15, questionFifthOptions);
                break;
            case 5:
                inventory.setItem(11, questionFirstOptions);
                inventory.setItem(12, questionSecondOptions);
                inventory.setItem(13, questionThirdOptions);
                inventory.setItem(14, questionFourthOptions);
                inventory.setItem(15, correctFifthOptions);
                break;
        }

        inventory.setItem(4, questionBook);
        inventory.setItem(3, netherStar);
        inventory.setItem(5, sunFlower);
        if (isCorrect) {
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
        } else {
            ItemStack glassPane = Util.getGlassPane(14);
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
        }

        return inventory;

    }
}
