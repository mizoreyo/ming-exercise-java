package site.mizore.exercise.dto;

import lombok.Data;

@Data
public class WrongQuestionParam {

    private Long questionId;
    private Long userId;
    private String questionName;
    private String wrongAnswer;

}
