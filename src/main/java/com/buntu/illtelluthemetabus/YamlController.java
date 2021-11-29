package com.buntu.illtelluthemetabus;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YamlController {
    private File questionFile;
    private YamlConfiguration questionYmlFile;

    public YamlController(File file) {
        this.questionFile = file;
        questionYmlFile = YamlConfiguration.loadConfiguration(questionFile);
        yamlInitialize();
    }

    private void yamlInitialize() {
        if (!questionFile.exists()) {
            System.out.println("question.yml 파일을 생성합니다.");
            questionYmlFile.set("question.기본문제.문제내용", "&f대전중앙고등학교의 교장은?");
            ArrayList<String> lores = new ArrayList<>();
            lores.add("&f대전중앙고등학교는 근본없는 고등학교이다.");
            lores.add("&f이딴 학교의 교장이라니 보나마나");
            lores.add("&e양봉마스터&f일 가능성이 농후하다.");
            questionYmlFile.set("question.기본문제.문제상세내용", lores.toArray());
            questionYmlFile.set("question.기본문제.선택지.1번", "&f꿀벌규");
            questionYmlFile.set("question.기본문제.선택지.2번", "&f양봉업자");
            questionYmlFile.set("question.기본문제.선택지.3번", "&f나뭇잎마을");
            questionYmlFile.set("question.기본문제.선택지.4번", "&f장수말벌");
            questionYmlFile.set("question.기본문제.선택지.5번", "&f양봉규");
            questionYmlFile.set("question.기본문제.난이도", "&a하");
            questionYmlFile.set("question.기본문제.정답", 5);
            questionYmlFile.set("question.기본문제.점수", 523);
            questionYmlFile.set("question.기본문제.제한시간", 523);

            questionYmlFile.set("question.심화문제.문제내용", "&f마크서버의 주소는?");
            lores.clear();
            lores.add("&f눈썰미가 좋다면 쉽게 알아낼 수 있다.");
            lores.add("&f사실 못 알아낸다.");
            questionYmlFile.set("question.심화문제.문제상세내용", lores.toArray());
            questionYmlFile.set("question.심화문제.선택지.1번", "&fbungu.ga");
            questionYmlFile.set("question.심화문제.선택지.2번", "&fwyvern.pw");
            questionYmlFile.set("question.심화문제.선택지.3번", "&fhitomi.la");
            questionYmlFile.set("question.심화문제.선택지.4번", "&fmc.aicoding.kro.kr");
            questionYmlFile.set("question.심화문제.선택지.5번", "&fexploit.in");
            questionYmlFile.set("question.심화문제.난이도", "&6중");
            questionYmlFile.set("question.심화문제.정답", 4);
            questionYmlFile.set("question.심화문제.점수", 123);
            questionYmlFile.set("question.심화문제.제한시간", 123);
            try {
                questionYmlFile.save(questionFile);
                System.out.println("qusetion.yml 예시 파일이 생성되었습니다.");
                System.out.println("알맞은 형식으로 문제를 만들어 주세요 :)");
            } catch (Exception ex) {
                System.out.println("question.yml 저장에 실패하였습니다.");
                ex.printStackTrace();
            }
        } else {
            yamlRetrieveData();
        }
    }

    public void yamlRetrieveData() {
        try {
            QuestionList.clearQuestion();

            for (String node : questionYmlFile.getConfigurationSection("question").getKeys(false)) {
                String questionTitle = String.valueOf(node);
                String questionContext = String.valueOf(questionYmlFile.get("question." + node + ".문제내용"));
                ArrayList<String> questionContextLores = new ArrayList<>(questionYmlFile.getStringList("question." + node + ".문제상세내용"));

                ArrayList<String> questionOptions = new ArrayList<>();
                for (int i = 1; i <= 5; i++) {
                    questionOptions.add(String.valueOf(questionYmlFile.get("question." + node + String.format(".선택지.%d번", i))));
                }

                String difficulty = String.valueOf(questionYmlFile.get("question." + node + ".난이도"));
                int questionAnswer = (int) questionYmlFile.get("question." + node + ".정답");
                int questionScore = (int) questionYmlFile.get("question." + node + ".점수");
                int questionLimitTime = (int) questionYmlFile.get("question." + node + ".제한시간");

                Question question = new Question(questionTitle, questionContext, questionContextLores, questionOptions, difficulty, questionAnswer, questionScore, questionLimitTime);
                QuestionList.putQuestion(question);
                System.out.println(String.format("%s 문제가 등록되었습니다.", questionTitle));
            }
        } catch (Exception ex) {
            System.out.println("question.yml 파일의 형식이 잘못되었습니다.");
            System.out.println("파일을 백업한 후, 서버를 재부팅하여 예시 파일 형식을 참조해주세요.");
            ex.printStackTrace();
        }
    }
}
