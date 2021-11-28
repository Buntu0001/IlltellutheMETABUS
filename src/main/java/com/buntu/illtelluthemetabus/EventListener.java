package com.buntu.illtelluthemetabus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        String inventoryTitle = inventoryClickEvent.getView().getTitle();
        if (QuestionList.containsQuestionByInventoryTitle(inventoryTitle)) {
            inventoryClickEvent.setCancelled(true);
        }
    }
}
