package site.mizore.exercise.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuestionParam {

    @NotNull
    @Min(value = 1,message = "不正确的问题类型")
    @Max(value = 4,message = "不正确的问题类型")
    @ApiModelProperty(value = "问题类型[1.选择题,2.多选题,3.填空题,4.判断题]",allowableValues = "range[1,4]",required = true)
    private Integer questionType;
    @NotEmpty
    @ApiModelProperty(value = "题目信息",required = true)
    private String questionInfo;
    @NotEmpty
    @ApiModelProperty(value = "答案",required = true)
    private String answer;
    @ApiModelProperty("解析")
    private String analysis;
    @ApiModelProperty(value = "题目难度[1.简单题,2.中等题,3.难题]",allowableValues = "range[1,3]")
    private Integer difficulty;
    @ApiModelProperty(value = "学科id",required = true)
    private Long subjectId;
    @ApiModelProperty("学科名")
    private String subjectName;
    @ApiModelProperty(value = "年级水平[1.小学,2.初中,3.高中,4.大学]",allowableValues = "range[1,4]")
    private Integer gradeLevel;
    @ApiModelProperty(value = "选项")
    private List<String> options;

}
