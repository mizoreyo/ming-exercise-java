package site.mizore.exercise.domain;

import lombok.Data;

import java.util.List;

@Data
public class PaperContent {

    private List<PaperQuestion> selectQuestions;
    private List<PaperQuestion> moreSelectQuestions;
    private List<PaperQuestion> fillQuestions;
    private List<PaperQuestion> judgeQuestions;

}
