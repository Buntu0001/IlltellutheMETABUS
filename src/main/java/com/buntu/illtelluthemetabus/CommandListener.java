package com.buntu.illtelluthemetabus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("문제")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("리로드")) {
                    player.sendMessage(Util.translate("&6[문제] &fquestion.yml 파일을 리로드합니다."));
                    Util.yamlManager.yamlRetrieveData();
                    return true;
                } else {
                    String questionTitle = args[0];
                    try {
                        Question question;
                        question = QuestionList.getQuestion(questionTitle);
                        player.sendMessage(Util.translate(String.format("&e문제이름: %s", question.getQuestionTitle())));
                        player.sendMessage(Util.translate(String.format("&6문제내용: %s", question.getQuestionContext())));
                        player.sendMessage(Util.translate("&3문제 상세 내용"));
                        int index = 0;
                        for (String lores : question.getQuestionContextLores()) {
                            player.sendMessage(Util.translate(String.format("%d번째 로어: %s", index + 1, lores)));
                            index++;
                        }

                        player.sendMessage(Util.translate("&d선택지"));
                        index = 0;
                        for (String options : question.getQuestionOptions()) {
                            player.sendMessage(Util.translate(String.format("&7%d번: %s", index + 1, options)));
                            index++;
                        }
                        player.sendMessage(Util.translate(String.format("&4난이도: %s", question.getDifficulty())));
                        player.sendMessage(Util.translate(String.format("&b정답: %d번", question.getQuestionAnswer())));
                        player.sendMessage(Util.translate(String.format("&a점수: %d점", question.getQuestionScore())));
                        player.sendMessage(Util.translate(String.format("&c제한시간: %d초", question.getQuestionLimitTime())));
                        return true;
                    } catch (Exception ex) {
                        player.sendMessage(Util.translate("&c[오류] &f알 수 없는 문제 이름입니다."));
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else if (label.equalsIgnoreCase("문제보기")) {
            if (args.length == 1) {
                String questionTitle = args[0];
                try {
                    if (QuestionPlayerStateList.containsQuestionPlayerState(player)) {
                        if (!QuestionPlayerStateList.getQuestionPlayerState(player).getSolvingQuestionState()) {
                            Question question;
                            question = QuestionList.getQuestion(questionTitle);
                            QuestionMisc.openQuestionGui(player, question);
                            return true;
                        } else {
                            player.sendMessage("&6[문제] &feeeeeeee이미 문제가 진행 중입니다!");
                            return true;
                        }
                    } else {
                        Question question;
                        question = QuestionList.getQuestion(questionTitle);
                        QuestionMisc.openQuestionGui(player, question);
                        return true;
                    }
                } catch (Exception ex) {
                    player.sendMessage(Util.translate("&c[오류] &f알 수 없는 문제 이름입니다."));
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
