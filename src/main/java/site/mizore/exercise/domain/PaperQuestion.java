package site.mizore.exercise.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaperQuestion {

    public static List<PaperQuestion> convertList(List<Question> questions,Integer score) {
        List<PaperQuestion> paperQuestions=new ArrayList<>();
        for (Question question : questions) {
            PaperQuestion paperQuestion=new PaperQuestion();
            paperQuestion.setQuestionId(question.getId());
            paperQuestion.setQuestionScore(score);
            paperQuestions.add(paperQuestion);
        }
        return paperQuestions;
    }

    private Long questionId;
    private String userAnswer;
    private Integer questionScore;
    private Integer questionUserScore;

}
