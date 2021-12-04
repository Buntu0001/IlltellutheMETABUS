package com.buntu.illtelluthemetabus;

import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        String inventoryTitle = inventoryClickEvent.getView().getTopInventory().getTitle();
        if (QuestionList.containsQuestion(inventoryTitle)) {
            inventoryClickEvent.setCancelled(true);
            Integer clickedInventorySLot = inventoryClickEvent.getSlot();
            Integer answerNumber = Util.determineAnswerNumber(clickedInventorySLot);
            if (QuestionPlayerStateList.containsQuestionPlayerState(player)) {
                QuestionPlayerState questionPlayerState = QuestionPlayerStateList.getQuestionPlayerState(player);
                if (questionPlayerState.getSolvingQuestionState()) {
                    if (clickedInventorySLot == 11 || clickedInventorySLot == 12 || clickedInventorySLot == 13 || clickedInventorySLot == 14 || clickedInventorySLot == 15) {
                        if (inventoryClickEvent.getCurrentItem().getType() != Material.AIR) {
                            if (StringUtils.isNotEmpty(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName())) {
                                if (questionPlayerState.getAllocatedQuestion().getQuestionAnswer() == answerNumber) {
                                    if (questionPlayerState.getGlassPaneColorState() == 0) {
                                        questionPlayerState.setGlassPaneColorState(5);
                                        player.getOpenInventory().close();
                                        Bukkit.getScheduler().scheduleSyncDelayedTask(Util.plugin, new Runnable() {
                                            @Override
                                            public void run() {
                                                QuestionMisc.completeQuestion(questionPlayerState);
                                            }
                                        }, 20L * 2);
                                    }
                                } else {
                                    if (questionPlayerState.getGlassPaneColorState() == 0) {
                                        questionPlayerState.setGlassPaneColorState(14);
                                        player.getOpenInventory().close();
                                        Bukkit.getScheduler().scheduleSyncDelayedTask(Util.plugin, new Runnable() {
                                            @Override
                                            public void run() {
                                                questionPlayerState.setGlassPaneColorState(0);
                                                player.getOpenInventory().close();
                                            }
                                        }, 20L * 2);
                                    }
                                }
                            }
                        }
                    }
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
                            QuestionMisc.makeQuestionGui(player, questionPlayerState.getAllocatedQuestion());
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
        //ScoreBoardManager.updateScoreBoard();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        if (QuestionPlayerStateList.containsQuestionPlayerState(player)) {
            if (QuestionPlayerStateList.getQuestionPlayerState(player).getSolvingQuestionState()) {
                QuestionMisc.interruptQuestion(QuestionPlayerStateList.getQuestionPlayerState(player));
                QuestionPlayerStateList.removeQuestionPlayerState(player);
                //ScoreBoardManager.updateScoreBoard();
            }
        }
    }
}
