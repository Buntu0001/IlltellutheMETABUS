package com.buntu.illtelluthemetabus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GameManager {
    private static Long globalTimer = 0L;
    private static HashMap<String, QuestionPlayerState> ranking = new HashMap<>();
    private static Integer limitTime = 3;
    private static Integer timerTaskId = 0;
    private static Integer countDownTaskId = 0;
    private static Integer count = 10;

    public static void setGlobalTimer(Long time) {
        globalTimer = time;
    }

    public static Long getGlobalTimer() {
        return globalTimer;
    }

    public static void setRanking(HashMap<String, QuestionPlayerState> rank) {
        ranking = rank;
    }

    public static HashMap<String, QuestionPlayerState> getRanking() {
        return ranking;
    }

    private static void saveAllInfo() {
        File file = new File(String.valueOf(Util.plugin.getDataFolder()));
        Random random = new Random();
        String generatedString = random.ints(48, 122 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(16)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String path = file + "/save_data_" + generatedString + ".log";
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(path))) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                QuestionPlayerState state = QuestionPlayerStateList.get(player.getName());
                writer.append(String.format(
                        "Player name: %s || Player Display Name: %s || Player Score: %d || Player solved count: %d\n",
                        player.getName(), player.getDisplayName(), state.getScore(), state.getSolvedCount()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void resetAllInfo() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            QuestionPlayerState state = QuestionPlayerStateList.get(player.getName());
            state.resetInfo();
        }
    }

    public static void gameStart() {
        if (!Util.questionAvailable) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(Util.translate("&a게임이 곧 시작됩니다!"), "", 0, 20 * 3, 0);
            }
            countDownTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Util.plugin, new Runnable() {
                @Override
                public void run() {
                    if (count > 0) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendTitle(Util.translate("&f게임 시작까지 " + "&6" + count + "초"), "", 0, 20, 0);
                        }
                        count--;
                    } else {
                        count = 10;
                        Bukkit.getScheduler().cancelTask(countDownTaskId);
                        countDownTaskId = 0;
                    }
                }
            }, 20L * 3, 20L);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Util.plugin, new Runnable() {
                @Override
                public void run() {
                    Util.questionAvailable = true;
                    globalTimer = System.currentTimeMillis() + (limitTime * 60000);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(Util.translate("&b게임시작!"), "", 0, 20 * 2, 0);
                        Location gameStartLocation = new Location(Util.plugin.getServer().getWorld("world"), -76, 5,
                                333);
                        if (!player.isOp()) {
                            player.teleport(gameStartLocation);
                        }
                    }
                }
            }, 20L * 13);
            timerTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Util.plugin, new Runnable() {
                @Override
                public void run() {
                    if (isGameEnd() || !Util.questionAvailable) {
                        Bukkit.broadcastMessage(Util.translate("&a[공지] 게임이 종료되었습니다."));
                        Location startLocation = new Location(Util.plugin.getServer().getWorld("world"), -26, 22, 352);
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendTitle(Util.translate("&c게임종료!"), "", 0, 20 * 3, 0);
                            player.teleport(startLocation);
                        }
                        Util.questionAvailable = false;
                        Bukkit.getScheduler().cancelTask(timerTaskId);
                        globalTimer = 0L;
                        timerTaskId = 0;
                        saveAllInfo();
                        resetAllInfo();
                    } else {
                        if (globalTimer - System.currentTimeMillis() <= 11 * 1000) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.sendTitle(
                                        Util.translate(
                                                "&f게임 종료까지 " + "&e"
                                                        + (int) (Math.floor(
                                                                (globalTimer - System.currentTimeMillis()) / 1000)))
                                                + "초",
                                        "", 0, 20, 0);
                            }
                        }
                    }
                }
            }, 20L * 13, 20L);
        }
    }

    public static void gameEnd() {
        if (Util.questionAvailable) {
            Util.questionAvailable = false;
        }
    }

    public static Boolean isGameEnd() {
        if (Util.questionAvailable) {
            if (Math.floor(globalTimer - System.currentTimeMillis()) < 1 * 1000) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
