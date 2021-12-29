package com.buntu.illtelluthemetabus;

import com.buntu.illtelluthemetabus.Util.GUI_TYPE;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class QuestionMisc {
    public static void initSolvingQuestion(Player player, Question question) {
        if (!QuestionPlayerStateList.get(player.getName()).isSolved(question)) {
            QuestionPlayerState state = QuestionPlayerStateList.get(player.getName());
            state.setQuestion(question);

            makeGUI(GUI_TYPE.NORMAL, player, question);
        } else {
            player.sendMessage(Util.translate("&c[METAVERSE] &7이미 푼 문제입니다."));
        }
    }

    public static void completeQuestion(QuestionPlayerState state) {
        state.setScore(state.getScore() + state.getQuestion().getScore());
        makeGUI(GUI_TYPE.END_CORRECT, state.getPlayer(), state.getQuestion());
        // state.setCompleteSolving(true);
        state.putSolvedQuestion(state.getQuestion());
        // state.setCompleteSolving(false);
        state.setQuestion(null);
    }

    public static void failQuestion(QuestionPlayerState state) {
        makeGUI(GUI_TYPE.END_INCORRECT, state.getPlayer(), state.getQuestion());
        // state.setCompleteSolving(true);
        state.putSolvedQuestion(state.getQuestion());
        // state.setCompleteSolving(false);
        state.setQuestion(null);
        /*
         * Bukkit.getScheduler().scheduleSyncDelayedTask(Util.plugin, new Runnable() {
         * 
         * @Override
         * public void run() {
         * state.getPlayer().closeInventory();
         * state.setCompleteSolving(false);
         * state.setQuestion(null);
         * }
         * 
         * 
         * }, 20L * 5);
         */
    }

    public static void makeGUI(Util.GUI_TYPE guiType, Player player, Question question) {
        if (!Objects.isNull(question)) {
            Inventory inventory = Bukkit.createInventory(null, 9 * 3, Util.translate("&0" + question.getTitle()));

            switch (guiType) {
                case NORMAL:
                    inventory = MakeGui.getNormalGUI(inventory, player, question);
                    player.openInventory(inventory);
                    break;
                case END_CORRECT:
                    inventory = MakeGui.getCommentaryGUI(inventory, player, question, true);
                    player.openInventory(inventory);
                    break;
                case END_INCORRECT:
                    inventory = MakeGui.getCommentaryGUI(inventory, player, question, false);
                    player.openInventory(inventory);
                    break;
                default:
                    System.out.println("Error on the makeQuestionGui");
                    System.out.println(player.getName());
                    System.out.println(question.getTitle());
            }
        }
    }
}
