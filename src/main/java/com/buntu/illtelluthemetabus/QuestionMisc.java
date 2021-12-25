package com.buntu.illtelluthemetabus;

import com.buntu.illtelluthemetabus.Util.GuiType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class QuestionMisc {
  public static void openQuestionGui(Player player, Question question) {
    QuestionPlayerState questionPlayerState = QuestionPlayerStateList.getQuestionPlayerState(player);
    questionPlayerState.setAllocatedQuestion(question);
    QuestionPlayerStateList.putQuestionPlayerState(questionPlayerState);

    makeQuestionGui(GuiType.NORMAL, player, question);
  }

  public static void completeQuestion(QuestionPlayerState questionPlayerState) {
    questionPlayerState.setPlayerScore(
        questionPlayerState.getPlayerScore() + questionPlayerState.getAllocatedQuestion().getQuestionScore());
    questionPlayerState.setAllocatedQuestion(null);
  }

  public static void makeQuestionGui(Util.GuiType guiType, Player player, Question question) {
    Inventory inventory = Bukkit.createInventory(null, 9 * 3, Util.translate("&0" + question.getQuestionTitle()));

    switch (guiType) {
      case NORMAL:
        break;
      case END:
        break;
      default:
        System.out.println("Error on the makeQuestionGui");
        System.out.println(player.getName());
        System.out.println(question.getQuestionTitle());
    }
  }
}
