package com.buntu.illtelluthemetabus;

import com.buntu.illtelluthemetabus.Util.GUI_TYPE;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.citizensnpcs.api.event.NPCRightClickEvent;

public class EventListener implements Listener {
  @EventHandler
  public void onPlayerClickInventory(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    String inventoryTitle = e.getView().getTopInventory().getTitle();
    if (QuestionList.contain(inventoryTitle)) {
      e.setCancelled(true);
      if (!QuestionPlayerStateList.get(player.getName()).getCompleteSolving()) {
        Integer clickedInventorySLot = e.getRawSlot();
        if (clickedInventorySLot >= 11 && clickedInventorySLot <= 15) {
          Integer answerNumber = Util.checkAnswer(clickedInventorySLot);
          Question question = QuestionList.get(inventoryTitle);
          QuestionPlayerState state = QuestionPlayerStateList.get(player.getName());
          if (answerNumber == question.getAnswer()) {
            // make commentary GUI
            QuestionMisc.completeQuestion(state);
          } else {
            // make commentary GUI
            QuestionMisc.failQuestion(state);
          }
        }
      }
    }
  }

  @EventHandler
  public void onPlayerNPCRightClickEvent(NPCRightClickEvent e) {
    if (Util.questionAvailable) {
      String npcName = e.getNPC().getName();
      Question question = QuestionList.getNPC(npcName);
      QuestionMisc.initSolvingQuestion(e.getClicker(), question);
    } else {
      e.getClicker().sendMessage(Util.translate("&c[METAVERSE] &7서비스 이용시간이 아닙니다."));
    }
  }

  @EventHandler
  public void onPlayerCloseInventory(InventoryCloseEvent e) {
    Player player = (Player) e.getPlayer();
    String inventoryTitle = e.getView().getTopInventory().getTitle();
    if (QuestionList.contain(inventoryTitle)) {
      if (QuestionPlayerStateList.get(player.getName()).getCompleteSolving()) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Util.plugin, new Runnable() {
          @Override
          public void run() {
            QuestionMisc.makeGUI(GUI_TYPE.END, player, QuestionPlayerStateList.get(player.getName()).getQuestion());
          }
        }, 1L);
      } else {
      }
    }
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    if (!QuestionPlayerStateList.contain(e.getPlayer().getName())) {
      QuestionPlayerState state = new QuestionPlayerState(e.getPlayer());
      QuestionPlayerStateList.put(state);
    } else {
      QuestionPlayerStateList.get(e.getPlayer().getName()).setPlayer(e.getPlayer());
    }
  }
}
