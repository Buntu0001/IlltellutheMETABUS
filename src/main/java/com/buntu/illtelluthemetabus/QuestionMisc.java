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
        QuestionPlayerState questionPlayerState;
        if (QuestionPlayerStateList.containsQuestionPlayerState(player)) {
            questionPlayerState = QuestionPlayerStateList.getQuestionPlayerState(player);
        } else {
            questionPlayerState = new QuestionPlayerState(player);
        }
        questionPlayerState.setAllocatedQuestion(question);
        questionPlayerState.setSolvingQuestionState(true);
        questionPlayerState.setTimer(question.getQuestionLimitTime());

        makeQuestionGui(player, question, question.getQuestionTitle());

        Bukkit.getScheduler().scheduleSyncDelayedTask(Util.plugin, new Runnable() {
            @Override
            public void run() {
                if (questionPlayerState.getSolvingQuestionState()) {
                    questionPlayerState.setSolvingQuestionState(false);
                    Bukkit.getScheduler().cancelTask(questionPlayerState.getTimerTaskId());
                    questionPlayerState.setTimer(0);
                    questionPlayerState.setTimerTaskId(0);
                    questionPlayerState.setAllocatedQuestion(null);
                    String inventoryTitle = player.getOpenInventory().getTitle();
                    if (QuestionList.containsQuestion(inventoryTitle)) {
                        player.closeInventory();
                    }
                    player.sendMessage("The END!");
                }
            }
        }, 20L * (questionPlayerState.getTimer() + 1));

        questionPlayerState.setTimerTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(Util.plugin, new Runnable() {
            @Override
            public void run() {
                if (questionPlayerState.getSolvingQuestionState()) {
                    updateInventoryTitle(player, question, Util.secondsToMinutes(questionPlayerState.getTimer()));
                    questionPlayerState.setTimer(questionPlayerState.getTimer() - 1);
                }
            }
        }, 0, 20L));

        QuestionPlayerStateList.putQuestionPlayerState(questionPlayerState);

    }

    public static void completeQuestion(QuestionPlayerState questionPlayerState) {
        questionPlayerState.setSolvingQuestionState(false);
        Bukkit.getScheduler().cancelTask(questionPlayerState.getTimerTaskId());
        questionPlayerState.setTimer(0);
        questionPlayerState.setTimerTaskId(0);
        questionPlayerState.setPlayerScore(questionPlayerState.getPlayerScore() + questionPlayerState.getAllocatedQuestion().getQuestionScore());
        questionPlayerState.setAllocatedQuestion(null);
        String inventoryTitle = questionPlayerState.getPlayer().getOpenInventory().getTitle();
        if (QuestionList.containsQuestion(inventoryTitle)) {
            questionPlayerState.getPlayer().closeInventory();
        }
    }

    public static void interruptQuestion(QuestionPlayerState questionPlayerState) {
        questionPlayerState.setSolvingQuestionState(false);
        Bukkit.getScheduler().cancelTask(questionPlayerState.getTimerTaskId());
        questionPlayerState.setTimer(0);
        questionPlayerState.setTimerTaskId(0);
        questionPlayerState.setAllocatedQuestion(null);
    }

    public static void updateInventoryTitle(Player player, Question question, Object[] remainingTime) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(entityPlayer.activeContainer.windowId,
                "minecraft:chest", new ChatMessage(ChatColor.translateAlternateColorCodes('&', Util.translate("&0" + question.getQuestionTitle() + String.format("&0" + Util.inventoryTitleSplitter + "남은시간: %s:%s", remainingTime[0], remainingTime[1])))),
                player.getOpenInventory().getTopInventory().getSize());
        entityPlayer.playerConnection.sendPacket(packet);
        entityPlayer.updateInventory(entityPlayer.activeContainer);
    }


    public static void makeQuestionGui(Player player, Question question, String inventoryTitle) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, Util.translate("&0" + inventoryTitle));

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

        player.openInventory(inventory);
    }
}
