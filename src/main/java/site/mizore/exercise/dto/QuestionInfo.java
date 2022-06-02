package site.mizore.exercise.dto;

import lombok.Data;

import java.util.List;

/**
 * 不包含答案的题目信息
 */
@Data
public class QuestionInfo {

    private Long id;
    private Integer questionType;
    private String questionInfo;
    private Integer difficulty;
    private Long subjectId;
    private String subjectName;
    private Integer gradeLevel;
    private List<String> options;

}
