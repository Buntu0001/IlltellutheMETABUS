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
            questionYmlFile.set("question.기본문제.문제내용", "대전중앙고등학교의 교장은?");
            questionYmlFile.set("question.기본문제.선택지.1번", "꿀벌규");
            questionYmlFile.set("question.기본문제.선택지.2번", "양봉업자");
            questionYmlFile.set("question.기본문제.선택지.3번", "나뭇잎마을");
            questionYmlFile.set("question.기본문제.선택지.4번", "장수말벌");
            questionYmlFile.set("question.기본문제.선택지.5번", "양봉규");
            questionYmlFile.set("question.기본문제.정답", 5);
            questionYmlFile.set("question.기본문제.점수", 523);
            questionYmlFile.set("question.기본문제.제한시간", 523);

            questionYmlFile.set("question.심화문제.문제내용", "마크서버의 주소는?");
            questionYmlFile.set("question.심화문제.선택지.1번", "bungu.ga");
            questionYmlFile.set("question.심화문제.선택지.2번", "wyvern.pw");
            questionYmlFile.set("question.심화문제.선택지.3번", "hitomi.la");
            questionYmlFile.set("question.심화문제.선택지.4번", "mc.aicoding.kro.kr");
            questionYmlFile.set("question.심화문제.선택지.5번", "exploit.in");
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
            try {
                for (String node : questionYmlFile.getConfigurationSection("question").getKeys(false)) {
                    String questionTitle = String.valueOf(node);
                    String questionContext = String.valueOf(questionYmlFile.get("question." + node + ".문제내용"));
                    ArrayList<String> questionOptions = new ArrayList<>();
                    for (int i = 1; i <= 5; i++) {
                        questionOptions.add(String.valueOf(questionYmlFile.get("question." + node + String.format(".선택지.%d번", i))));
                    }
                    int questionAnswer = (int) questionYmlFile.get("question." + node + ".정답");
                    int questionScore = (int) questionYmlFile.get("question." + node + ".점수");
                    int questionLimitTime = (int) questionYmlFile.get("question." + node + ".제한시간");

                    Question question = new Question(questionTitle, questionContext, questionOptions, questionAnswer, questionScore, questionLimitTime);
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
}
