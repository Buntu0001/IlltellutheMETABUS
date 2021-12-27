package com.buntu.illtelluthemetabus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

  public File pluginFolder = new File(String.valueOf(getDataFolder()));

  @Override
  public void onEnable() {
    System.out.println("문제 플러그인이 활성화되었습니다.");
    System.out.println("문제 내용은 question.yml 파일을 참조해주세요!");
    Util.plugin = this;
    checkPluginFolderExist();
    Util.yamlManager = new YamlManager(new File(pluginFolder + "/question.yml"));
    System.out.println(pluginFolder.getPath());
    Util.initaillizeGlassPane();

    ScoreBoardManager.setScoreBoardTitle("AICodingMETAVERSE");
    ScoreBoardManager.repeatUpdateScoreBoard();

    getCommand("문제").setExecutor(new CommandListener());
    getCommand("문제보기").setExecutor(new CommandListener());
    getServer().getPluginManager().registerEvents(new EventListener(), this);
  }

  @Override
  public void onDisable() {
    System.out.println("문제 플러그인이 비활성화되었습니다.");
  }

  private void checkPluginFolderExist() {
    if (!pluginFolder.exists()) {
      System.out.println("플러그인 폴더가 존재하지 않습니다.");
      System.out.println("폴더를 생성합니다.");
      pluginFolder.mkdir();
    }
  }
}
