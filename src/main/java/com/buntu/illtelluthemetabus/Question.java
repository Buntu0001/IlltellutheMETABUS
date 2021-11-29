package com.buntu.illtelluthemetabus;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class Question {
    private String questionTitle;
    private String questionContext;
    private ArrayList<String> questionContextLores;
    private ArrayList<String> questionOptions = new ArrayList<>();
    private String difficulty;
    private int questionAnswer;
    private int questionScore;
    private int questionLimitTime;

    public Question(String title, String context, ArrayList<String> lores, ArrayList<String> options, String difficulty, int answer, int score, int limitTime) {
        this.questionTitle = title;
        this.questionContext = context;
        this.questionContextLores = lores;
        this.questionOptions = options;
        this.difficulty = difficulty;
        this.questionAnswer = answer;
        this.questionScore = score;
        this.questionLimitTime = limitTime;

    }

    public String getQuestionTitle() {
        return this.questionTitle;
    }

    public String getQuestionContext() {
        return this.questionContext;
    }

    public ArrayList<String> getQuestionContextLores() {
        return this.questionContextLores;
    }

    public ArrayList<String> getQuestionOptions() {
        return this.questionOptions;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public int getQuestionAnswer() {
        return this.questionAnswer;
    }

    public int getQuestionScore() {
        return this.questionScore;
    }

    public int getQuestionLimitTime() {
        return this.questionLimitTime;
    }


}

class QuestionList {
    private static HashMap<String, Question> questionMap = new HashMap<>();

    public static void putQuestion(Question question) {
        questionMap.put(question.getQuestionTitle(), question);
    }

    public static void removeQuestion(String questionTitle) {
        questionMap.remove(questionTitle);
    }

    public static Question getQuestion(String questionTitle) {
        return questionMap.get(questionTitle);
    }

    public static Boolean containsQuestion(String questionTitle) {
        return questionMap.containsKey(ChatColor.stripColor(questionTitle));
    }

    public static void clearQuestion() {
        questionMap.clear();
    }
}

class QuestionPlayerState {
    private Question allocatedQuestion;
    private Player player;
    private Boolean solvingQuestionState = false;
    private Integer score = 0;
    private Integer timer = 0;
    private Integer timerTaskId = 0;

    public QuestionPlayerState(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setAllocatedQuestion(Question question) {
        this.allocatedQuestion = question;
    }

    public Question getAllocatedQuestion() {
        return allocatedQuestion;
    }

    public void setSolvingQuestionState(Boolean state) {
        this.solvingQuestionState = state;
    }

    public void setTimer(Integer seconds) {
        this.timer = seconds;
    }

    public void setTimerTaskId(Integer taskId) {
        this.timerTaskId = taskId;
    }

    public Boolean getSolvingQuestionState() {
        return solvingQuestionState;
    }

    public void setPlayerScore(Integer playerScore) {
        this.score = playerScore;
    }

    public Integer getPlayerScore() {
        return this.score;
    }

    public Integer getTimer() {
        return this.timer;
    }

    public Integer getTimerTaskId() {
        return this.timerTaskId;
    }
}

class QuestionPlayerStateList {
    private static HashMap<Player, QuestionPlayerState> questionPlayerStateMap = new HashMap<>();

    public static void putQuestionPlayerState(QuestionPlayerState questionPlayerState) {
        questionPlayerStateMap.put(questionPlayerState.getPlayer(), questionPlayerState);
    }

    public static QuestionPlayerState getQuestionPlayerState(Player player) {
        return questionPlayerStateMap.get(player);
    }

    public static Boolean containsQuestionPlayerState(Player player) {
        return questionPlayerStateMap.containsKey(player);
    }
}

class QuestionRanking {
    private static HashMap<String, Integer> questionRanking = new HashMap<String, Integer>();

    /*public ArrayList<HashMap<String, Integer>> getSortedRanking() {

    }*/

    public void putRankingTable(String playerDisplayName, Integer playerScore) {

    }
}

class QuestionMisc {
    public static void openQuestionGui(Player player, Question question) {
        QuestionPlayerState questionPlayerState = new QuestionPlayerState(player);
        questionPlayerState.setAllocatedQuestion(question);
        questionPlayerState.setSolvingQuestionState(true);
        questionPlayerState.setTimer(question.getQuestionLimitTime());

        Bukkit.getScheduler().scheduleSyncDelayedTask(Util.plugin, new Runnable() {
            @Override
            public void run() {
                if (questionPlayerState.getSolvingQuestionState()) {
                    questionPlayerState.setSolvingQuestionState(false);
                    Bukkit.getScheduler().cancelTask(questionPlayerState.getTimerTaskId());
                    questionPlayerState.setTimer(0);
                    questionPlayerState.setTimerTaskId(0);
                    questionPlayerState.setAllocatedQuestion(null);
                    if (QuestionList.containsQuestion(player.getOpenInventory().getTitle())) {
                        player.closeInventory();
                    }
                    player.sendMessage("The END!");
                }
            }
        }, (20L * questionPlayerState.getTimer()) + 1);

        questionPlayerState.setTimerTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(Util.plugin, new Runnable() {
            @Override
            public void run() {
                Object[] timerFormatted = Util.secondsToMinutes(questionPlayerState.getTimer());
                player.sendMessage(String.format("%s분 %s초 남음", String.valueOf(timerFormatted[0]), String.valueOf(timerFormatted[1])));
                questionPlayerState.setTimer(questionPlayerState.getTimer() - 1);
            }
        }, 0, 20L));

        QuestionPlayerStateList.putQuestionPlayerState(questionPlayerState);

        makeQuestionGui(player, question);
    }

    public static void makeQuestionGui(Player player, Question question) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, Util.translate("&e" + question.getQuestionTitle()));

        ItemStack questionBook = new ItemStack(Material.ENCHANTED_BOOK); // slot 4
        ItemMeta questionBookMeta = questionBook.getItemMeta();
        questionBookMeta.setDisplayName(Util.translate(question.getQuestionContext()));
        questionBookMeta.setLore(Util.translate(question.getQuestionContextLores()));
        questionBook.setItemMeta(questionBookMeta);

        // slot 11 ~ 15

        ItemStack questionFirstOptions = new ItemStack(4870, 1, (short) 8);
        ItemMeta questionFirstOptionsMeta = questionFirstOptions.getItemMeta();
        questionFirstOptionsMeta.setDisplayName(Util.translate(question.getQuestionOptions().get(0)));
        questionFirstOptions.setItemMeta(questionFirstOptionsMeta);

        ItemStack questionSecondOptions = new ItemStack(4871, 1, (short) 0);
        ItemMeta questionSecondOptionsMeta = questionSecondOptions.getItemMeta();
        questionSecondOptionsMeta.setDisplayName(Util.translate(question.getQuestionOptions().get(1)));
        questionSecondOptions.setItemMeta(questionSecondOptionsMeta);

        ItemStack questionThirdOptions = new ItemStack(4871, 1, (short) 8);
        ItemMeta questionThirdOptionsMeta = questionThirdOptions.getItemMeta();
        questionThirdOptionsMeta.setDisplayName(Util.translate(question.getQuestionOptions().get(2)));
        questionThirdOptions.setItemMeta(questionThirdOptionsMeta);

        ItemStack questionFourthOptions = new ItemStack(4872, 1, (short) 0);
        ItemMeta questionFourthOptionsMeta = questionFourthOptions.getItemMeta();
        questionFourthOptionsMeta.setDisplayName(Util.translate(question.getQuestionOptions().get(3)));
        questionFourthOptions.setItemMeta(questionFourthOptionsMeta);

        ItemStack questionFifthOptions = new ItemStack(4872, 1, (short) 8);
        ItemMeta questionFifthOptionsMeta = questionFifthOptions.getItemMeta();
        questionFifthOptionsMeta.setDisplayName(Util.translate(question.getQuestionOptions().get(4)));
        questionFifthOptions.setItemMeta(questionFifthOptionsMeta);

        inventory.setItem(4, questionBook);
        inventory.setItem(11, questionFirstOptions);
        inventory.setItem(12, questionSecondOptions);
        inventory.setItem(13, questionThirdOptions);
        inventory.setItem(14, questionFourthOptions);
        inventory.setItem(15, questionFifthOptions);

        player.openInventory(inventory);
    }
}
