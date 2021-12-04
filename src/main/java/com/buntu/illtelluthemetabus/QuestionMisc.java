package com.buntu.illtelluthemetabus;

import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenWindow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuestionMisc {
    public static void openQuestionGui(Player player, Question question) {
        QuestionPlayerState questionPlayerState = QuestionPlayerStateList.getQuestionPlayerState(player);
        questionPlayerState.setAllocatedQuestion(question);
        questionPlayerState.setSolvingQuestionState(true);
        questionPlayerState.setMilliSeconds(System.currentTimeMillis() + ((question.getQuestionLimitTime() + 1) * 1000L));
        questionPlayerState.setGlassPaneColorState(0);
        makeQuestionGui(player, question);

        questionPlayerState.setUpdateTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(Util.plugin, new Runnable() {
            @Override
            public void run() {
                updateInventoryTitle(player, question, Util.secondsToMinutes(questionPlayerState.getMilliSeconds()));
            }
        }, 0, 5L));

        questionPlayerState.setMilliTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(Util.plugin, new Runnable() {
            @Override
            public void run() {
                if (questionPlayerState.getSolvingQuestionState()) {
                    if (isTimeExpired(questionPlayerState)) {
                        questionPlayerState.setSolvingQuestionState(false);
                        Bukkit.getScheduler().cancelTask(questionPlayerState.getMilliTaskId());
                        Bukkit.getScheduler().cancelTask(questionPlayerState.getUpdateTaskId());
                        questionPlayerState.setMilliSeconds(0L);
                        questionPlayerState.setMilliTaskId(0);
                        questionPlayerState.setUpdateTaskId(0);
                        questionPlayerState.setAllocatedQuestion(null);
                        questionPlayerState.setGlassPaneColorState(0);
                        String inventoryTitle = player.getOpenInventory().getTitle();
                        if (QuestionList.containsQuestion(inventoryTitle)) {
                            player.closeInventory();
                        }
                        player.sendMessage(Util.translate("&c시간 초과!"));
                    }
                } else {
                    Bukkit.getScheduler().cancelTask(questionPlayerState.getMilliTaskId());
                    Bukkit.getScheduler().cancelTask(questionPlayerState.getUpdateTaskId());
                }
            }
        }, 0, 1L));

        QuestionPlayerStateList.putQuestionPlayerState(questionPlayerState);

    }

    public static Boolean isTimeExpired(QuestionPlayerState questionPlayerState) {
        return (questionPlayerState.getMilliSeconds() - System.currentTimeMillis()) / 1000 < 0;
    }

    public static void completeQuestion(QuestionPlayerState questionPlayerState) {
        questionPlayerState.setSolvingQuestionState(false);
        Bukkit.getScheduler().cancelTask(questionPlayerState.getMilliTaskId());
        Bukkit.getScheduler().cancelTask(questionPlayerState.getUpdateTaskId());
        questionPlayerState.setMilliSeconds(0L);
        questionPlayerState.setMilliTaskId(0);
        questionPlayerState.setUpdateTaskId(0);
        questionPlayerState.setPlayerScore(questionPlayerState.getPlayerScore() + questionPlayerState.getAllocatedQuestion().getQuestionScore());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Util.plugin, new Runnable() {
            @Override
            public void run() {
                String inventoryTitle = questionPlayerState.getPlayer().getOpenInventory().getTitle();
                if (QuestionList.containsQuestion(inventoryTitle)) {
                    questionPlayerState.getPlayer().closeInventory();
                    questionPlayerState.setAllocatedQuestion(null);
                    questionPlayerState.setGlassPaneColorState(0);
                }
            }
        }, 20L * 3);
    }

    public static void interruptQuestion(QuestionPlayerState questionPlayerState) {
        questionPlayerState.setSolvingQuestionState(false);
        Bukkit.getScheduler().cancelTask(questionPlayerState.getMilliTaskId());
        Bukkit.getScheduler().cancelTask(questionPlayerState.getUpdateTaskId());
        questionPlayerState.setMilliSeconds(0L);
        questionPlayerState.setMilliTaskId(0);
        questionPlayerState.setUpdateTaskId(0);
        questionPlayerState.setAllocatedQuestion(null);
    }

    public static void updateInventoryTitle(Player player, Question question, Object[] remainingTime) {
        if (QuestionPlayerStateList.containsQuestionPlayerState(player)) {
            QuestionPlayerState questionPlayerState = QuestionPlayerStateList.getQuestionPlayerState(player);
            if (questionPlayerState.getSolvingQuestionState()) {
                if (!isTimeExpired(questionPlayerState)) {
                    EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
                    PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(entityPlayer.activeContainer.windowId,
                            "minecraft:chest", new ChatMessage(ChatColor.translateAlternateColorCodes('&', Util.translate("&0" + question.getQuestionTitle() + String.format("&0" + Util.inventoryTitleSplitter + "남은시간: %s:%s", remainingTime[0], remainingTime[1])))),
                            player.getOpenInventory().getTopInventory().getSize());
                    entityPlayer.playerConnection.sendPacket(packet);
                    entityPlayer.updateInventory(entityPlayer.activeContainer);
                }
            }
        }
    }


    public static void makeQuestionGui(Player player, Question question) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, Util.translate("&0" + question.getQuestionTitle()));

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

        Integer colorCode = QuestionPlayerStateList.getQuestionPlayerState(player).getGlassPaneColorState();
        ItemStack glassPane = Util.getGlassPane(colorCode);
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

        player.openInventory(inventory);
    }
}
