package com.buntu.illtelluthemetabus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class ScoreBoardManager {
    private static Integer count = 0;
    private static String scoreBoardTitle;
    private static Integer updateTaskId = 0;

    public static void setScoreBoardTitle(String title) {
        scoreBoardTitle = title;
    }

    public static String getScoreBoardTitle() {
        return scoreBoardTitle;
    }

    public static void repeatUpdateScoreBoard() {
        updateTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Util.plugin, new Runnable() {
            @Override
            public void run() {
                ScoreBoardManager.updateScoreBoard();
            }
        }, 0, 2L);
    }

    public static void updateScoreBoard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        String completeTitle = Util.animatedString(scoreBoardTitle, count++);
        ArrayList<QuestionPlayerState> sortList = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.isOp()) {
                sortList.add(QuestionPlayerStateList.get(player.getName()));
            }
        }
        sortList = Util.reverseSequentialSort(sortList);
        if (count == scoreBoardTitle.length() + 3) {
            count = 0;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Bukkit.getOnlinePlayers().size() > 0) {
                if (!player.isOp()) {
                    org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();
                    Objective obj = board.registerNewObjective(player.getName(), "dummy");
                    obj.setDisplayName(completeTitle);
                    obj.setDisplaySlot(DisplaySlot.SIDEBAR);

                    Integer multipleLine = Bukkit.getOnlinePlayers().size() + 4;

                    Score nicknameScore = obj.getScore(Util.translate(" &7닉네임: &6" + player.getDisplayName()));
                    nicknameScore.setScore(multipleLine--);

                    Score solvedCount = obj
                            .getScore(
                                    Util.translate(" &7푼 문제: &6" + QuestionPlayerStateList.get(player.getName()).getSolvedCount() + "개"));
                    solvedCount.setScore(multipleLine--);

                    Score spilitter_1 = obj.getScore("");
                    spilitter_1.setScore(multipleLine--);

                    Score rank = obj.getScore(Util.translate(" &e랭킹 &o(오름차순)"));
                    rank.setScore(multipleLine--);

                    for (int i = 0; i < sortList.size(); i++) {
                        QuestionPlayerState state = sortList.get(i);
                        Score score = obj.getScore(
                                Util.translate(String.format("  &a%s: &c%d점", state.getPlayer().getDisplayName(), state.getScore())));
                        score.setScore(multipleLine--);
                    }

                    player.setScoreboard(board);
                }
            }
        }
    }

    public static void clearScoreBoard() {
        Bukkit.getScheduler().cancelTask(updateTaskId);
        ScoreboardManager scoreBoardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreBoardManager.getNewScoreboard();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.setScoreboard(scoreboard);
        }
    }
}
