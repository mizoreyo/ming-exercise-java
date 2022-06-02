package site.mizore.exercise.dto;

import lombok.Data;

@Data
public class CreatePaperParam {

    private String paperName;
    private String subjectName;
    private Integer gradeLevel;

}
