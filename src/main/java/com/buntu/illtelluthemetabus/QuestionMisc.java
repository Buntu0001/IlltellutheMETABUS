package com.buntu.illtelluthemetabus;

import com.buntu.illtelluthemetabus.Util.GUI_TYPE;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class QuestionMisc {
  public static void initSolvingQuestion(Player player, Question question) {
    QuestionPlayerState state = QuestionPlayerStateList.get(player);
    state.setQuestion(question);
    QuestionPlayerStateList.put(state);

    makeGUI(GUI_TYPE.NORMAL, player, question);
  }

  public static void completeQuestion(QuestionPlayerState state) {
    state.setScore(
        state.getScore() + state.getQuestion().getScore());

    // open commentary GUI maybe
    state.setQuestion(null);
  }

  public static void makeGUI(Util.GUI_TYPE guiType, Player player, Question question) {
    Inventory inventory = Bukkit.createInventory(null, 9 * 3, Util.translate("&0" + question.getTitle()));

    switch (guiType) {
      case NORMAL:
        // open normal GUI
        break;
      case END:
        // open commentary GUI
        break;
      default:
        System.out.println("Error on the makeQuestionGui");
        System.out.println(player.getName());
        System.out.println(question.getTitle());
    }
  }
}
