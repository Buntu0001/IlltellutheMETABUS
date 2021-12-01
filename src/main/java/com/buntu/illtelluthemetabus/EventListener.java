package com.buntu.illtelluthemetabus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        String inventoryTitle = inventoryClickEvent.getView().getTitle();
        if (QuestionList.containsQuestion(inventoryTitle)) {
            inventoryClickEvent.setCancelled(true);
            Integer clickedInventorySLot = inventoryClickEvent.getSlot();
            Integer answerNumber = Util.determineAnswerNumber(clickedInventorySLot);
            if (QuestionPlayerStateList.containsQuestionPlayerState(player)) {
                if (QuestionPlayerStateList.getQuestionPlayerState(player).getAllocatedQuestion().getQuestionAnswer() == answerNumber) {
                    player.sendMessage("정답!");
                    QuestionMisc.completeQuestion(QuestionPlayerStateList.getQuestionPlayerState(player));
                } else {
                    player.sendMessage("오답!");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent inventoryCloseEvent) {
        Player player = (Player) inventoryCloseEvent.getPlayer();
        String inventoryTitle = inventoryCloseEvent.getView().getTitle();
        QuestionPlayerState questionPlayerState = QuestionPlayerStateList.getQuestionPlayerState(player);
        if (QuestionList.containsQuestion(inventoryTitle)) {
            if (QuestionPlayerStateList.containsQuestionPlayerState(player)) {
                if (questionPlayerState.getSolvingQuestionState()) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Util.plugin, new Runnable() {
                        @Override
                        public void run() {
                            player.openInventory(inventoryCloseEvent.getInventory());
                        }
                    }, 1L);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        QuestionPlayerState questionPlayerState = new QuestionPlayerState(playerJoinEvent.getPlayer());
        QuestionPlayerStateList.putQuestionPlayerState(questionPlayerState);
        ScoreBoardManager.initializeScoreBoard();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        if (QuestionPlayerStateList.containsQuestionPlayerState(player)) {
            if (QuestionPlayerStateList.getQuestionPlayerState(player).getSolvingQuestionState()) {
                QuestionMisc.interruptQuestion(QuestionPlayerStateList.getQuestionPlayerState(player));
            }
        }
    }
}
