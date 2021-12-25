package com.buntu.illtelluthemetabus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
  @EventHandler
  public void onPlayerClickInventory(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    String inventoryTitle = e.getView().getTopInventory().getTitle();
    if (QuestionList.contain(inventoryTitle)) {
      e.setCancelled(true);
      Integer clickedInventorySLot = e.getSlot();
      Integer answerNumber = Util.checkAnswer(clickedInventorySLot);
      Question question = QuestionList.get(inventoryTitle);
      if (answerNumber == question.getAnswer()) {
        // make commentary GUI
      } else {
        // make commentary GUI
      }
    }
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    QuestionPlayerState state = new QuestionPlayerState(e.getPlayer());
    QuestionPlayerStateList.put(state);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e) {
    Player player = e.getPlayer();
    QuestionPlayerState state = QuestionPlayerStateList.get(player);
    state = null;
    QuestionPlayerStateList.remove(player);
  }
}
