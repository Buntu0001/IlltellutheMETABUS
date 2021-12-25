package com.buntu.illtelluthemetabus;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

public class YamlManager {
  private File settingFile;
  private YamlConfiguration settingYml;

  public YamlManager(File file) {
    this.settingFile = file;
    settingYml = YamlConfiguration.loadConfiguration(settingFile);
    yamlInitialize();
  }

  private void yamlInitialize() {
    if (!settingFile.exists()) {
      System.out.println("question.yml 파일을 생성합니다.");
      settingYml.set("question.기본문제.문제내용", "&f대전중앙고등학교의 교장은?");
      ArrayList<String> lores = new ArrayList<>();
      lores.add("&f대전중앙고등학교는 근본없는 고등학교이다.");
      lores.add("&f이딴 학교의 교장이라니 보나마나");
      lores.add("&e양봉마스터&f일 가능성이 농후하다.");
      settingYml.set("question.기본문제.문제상세내용", lores.toArray());
      lores.clear();
      lores.add("&f이것은 해설일까 아닐까?");
      lores.add("&f그것은 아무도 모른다.");
      settingYml.set("question.기본문제.문제해설", lores.toArray());
      settingYml.set("question.기본문제.선택지.1번", "&f꿀벌규");
      settingYml.set("question.기본문제.선택지.2번", "&f양봉업자");
      settingYml.set("question.기본문제.선택지.3번", "&f나뭇잎마을");
      settingYml.set("question.기본문제.선택지.4번", "&f장수말벌");
      settingYml.set("question.기본문제.선택지.5번", "&f양봉규");
      settingYml.set("question.기본문제.난이도", "&a하");
      settingYml.set("question.기본문제.정답", 5);
      settingYml.set("question.기본문제.점수", 523);

      settingYml.set("question.심화문제.문제내용", "&f마크서버의 주소는?");
      lores.clear();
      lores.add("&f눈썰미가 좋다면 쉽게 알아낼 수 있다.");
      lores.add("&f사실 못 알아낸다.");
      settingYml.set("question.심화문제.문제상세내용", lores.toArray());
      lores.clear();
      lores.add("&f이것은 해설일까 아닐까?");
      lores.add("&f그것은 아무도 모른다.");
      settingYml.set("question.심화문제.문제해설", lores.toArray());
      settingYml.set("question.심화문제.선택지.1번", "&fbungu.ga");
      settingYml.set("question.심화문제.선택지.2번", "&fwyvern.pw");
      settingYml.set("question.심화문제.선택지.3번", "&fhitomi.la");
      settingYml.set("question.심화문제.선택지.4번", "&fmc.aicoding.kro.kr");
      settingYml.set("question.심화문제.선택지.5번", "&fexploit.in");
      settingYml.set("question.심화문제.난이도", "&6중");
      settingYml.set("question.심화문제.정답", 4);
      settingYml.set("question.심화문제.점수", 123);

      try {
        settingYml.save(settingFile);
        System.out.println("qusetion.yml 예시 파일이 생성되었습니다.");
        System.out.println("알맞은 형식으로 문제를 만들어 주세요 :)");
      } catch (Exception ex) {
        System.out.println("question.yml 저장에 실패하였습니다.");
        ex.printStackTrace();
      }
    } else {
      retrieveData();
    }
  }

  public void retrieveData() {
    try {
      QuestionList.clear();

      for (String node : settingYml.getConfigurationSection("question").getKeys(false)) {
        String title = String.valueOf(node);
        String context = String.valueOf(settingYml.get("question." + node + ".문제내용"));
        ArrayList<String> lores = new ArrayList<>(
            settingYml.getStringList("question." + node + ".문제상세내용"));

        ArrayList<String> commentary = new ArrayList<>(
            settingYml.getStringList("question." + node + ".문제해설"));

        ArrayList<String> options = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
          options.add(String.valueOf(settingYml.get("question." + node + String.format(".선택지.%d번", i))));
        }

        String difficulty = String.valueOf(settingYml.get("question." + node + ".난이도"));
        int answer = (int) settingYml.get("question." + node + ".정답");
        int score = (int) settingYml.get("question." + node + ".점수");

        Question question = new Question(title, context, lores, commentary,
            options,
            difficulty, answer, score);
        QuestionList.put(question);
        System.out.println(String.format("%s 문제가 등록되었습니다.", title));
      }
    } catch (Exception ex) {
      System.out.println("question.yml 파일의 형식이 잘못되었습니다.");
      System.out.println("파일을 백업한 후, 서버를 재부팅하여 예시 파일 형식을 참조해주세요.");
      ex.printStackTrace();
    }
  }
}
