package com.buntu.illtelluthemetabus;

import com.buntu.illtelluthemetabus.Util.GUI_TYPE;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.inventory.Inventory;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String inventoryTitle = e.getView().getTopInventory().getTitle();
        if (QuestionList.contain(inventoryTitle)) {
            e.setCancelled(true);
            if (Util.checkGUIType(e.getInventory()) == GUI_TYPE.NORMAL) {
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
                } else if (clickedInventorySLot == 5) {
                    player.closeInventory();
                    player.sendTitle(Util.translate("&c포기 멈춰!"), "", 0, 20 * 3, 0);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerNPCRightClickEvent(NPCRightClickEvent e) {
        if (Util.questionAvailable) {
            String npcName = ChatColor.stripColor(e.getNPC().getName());
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
        Inventory inventory = e.getInventory();
        if (QuestionList.contain(inventoryTitle)) {
            if (QuestionPlayerStateList.get(player.getName()).getCompleteSolving()) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Util.plugin, new Runnable() {
                    @Override
                    public void run() {
                        // QuestionMisc.makeGUI(GUI_TYPE.END_CORRECT, player,
                        // QuestionPlayerStateList.get(player.getName()).getQuestion());
                        if (QuestionPlayerStateList.get(player.getName()).getCompleteSolving()) {
                            // player.openInventory(inventory);
                        }
                    }
                }, 1L);
            } else {
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!QuestionPlayerStateList.contain(player.getName())) {
            QuestionPlayerState state = new QuestionPlayerState(player);
            QuestionPlayerStateList.put(state);
        } else {
            QuestionPlayerStateList.get(player.getName()).setPlayer(player);
        }
        if (!player.isOp()) {
            Location startLocation = new Location(Util.plugin.getServer().getWorld("world"), -26, 22, 352);
            player.teleport(startLocation);
        }
    }
}
