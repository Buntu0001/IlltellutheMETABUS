package com.buntu.illtelluthemetabus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        String inventoryTitle = inventoryClickEvent.getView().getTitle();
        if (QuestionList.containsQuestion(inventoryTitle)) {
            inventoryClickEvent.setCancelled(true);
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
}
