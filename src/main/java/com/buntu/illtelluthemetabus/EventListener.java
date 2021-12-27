package com.buntu.illtelluthemetabus;

import com.buntu.illtelluthemetabus.Util.GUI_TYPE;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.minecraft.server.v1_12_R1.Entity;

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
      QuestionPlayerState state = QuestionPlayerStateList.get(player);
      if (answerNumber == question.getAnswer()) {
        // make commentary GUI
        QuestionMisc.completeQuestion(state);
      } else {
        // make commentary GUI
        QuestionMisc.failQuestion(state);
      }
    }
  }

  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
    Player player = e.getPlayer();
    Entity entity = (Entity) e.getRightClicked();
    Bukkit.broadcastMessage(player.getName() + " Clicked NPC " + entity.getName());
  }

  @EventHandler
  public void onPlayerCloseInventory(InventoryCloseEvent e) {
    Player player = (Player) e.getPlayer();
    String inventoryTitle = e.getView().getTopInventory().getTitle();
    if (QuestionList.contain(inventoryTitle)) {
      if (QuestionPlayerStateList.get(player).getCompleteSolving() == true) {
        QuestionMisc.makeGUI(GUI_TYPE.END, player, QuestionPlayerStateList.get(player).getQuestion());
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
    QuestionPlayerStateList.remove(player);
  }
}
