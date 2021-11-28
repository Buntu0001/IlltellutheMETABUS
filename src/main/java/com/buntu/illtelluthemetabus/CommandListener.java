package com.buntu.illtelluthemetabus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;

public class CommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("문제")) {
            if (args.length == 1) {
                String questionTitle = args[0];
                try {
                    Question question;
                    question = QuestionList.getQuestion(questionTitle);
                    player.sendMessage(Util.translate(String.format("&e문제이름: &f%s", question.getQuestionTitle())));
                    player.sendMessage(Util.translate(String.format("&6문제내용: &f%s", question.getQuestionContext())));

                    player.sendMessage(Util.translate("&d선택지"));
                    ArrayList<String> questionOptions = question.getQuestionOptions();
                    for (int i = 0; i < 5; i++) {
                        player.sendMessage(Util.translate(String.format("&7%d번: &f%s", i + 1, questionOptions.get(i))));
                    }

                    player.sendMessage(Util.translate(String.format("&b정답: &f%d번", question.getQuestionAnswer())));
                    player.sendMessage(Util.translate(String.format("&a점수: &f%d점", question.getQuestionScore())));
                    player.sendMessage(Util.translate(String.format("&c제한시간: &f%d초", question.getQuestionLimitTime())));
                } catch (Exception ex) {
                    player.sendMessage(Util.translate("&c[오류] &f알 수 없는 문제 이름입니다."));
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
