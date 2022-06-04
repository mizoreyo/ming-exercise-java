package site.mizore.exercise.dto;

import lombok.Data;

@Data
public class PaperParam {

    private String name;
    private Long subjectId;
    private String subjectName;
    private Integer gradeLevel;
    private Long userId;
    private Integer complete;
    private Integer userScore;

}
