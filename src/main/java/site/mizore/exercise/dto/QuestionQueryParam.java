package site.mizore.exercise.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 获取题目的参数
 */
@Data
public class QuestionQueryParam {

    @ApiModelProperty(value = "查询个数")
    private Integer limit;

    @ApiModelProperty(value = "问题类型[1.选择题,2.多选题,3.填空题,4.判断题]",allowableValues = "range[1,4]",required = true)
    private List<Integer> questionTypeList;

    @ApiModelProperty(value = "题目难度[1.简单题,2.中等题,3.难题]",allowableValues = "range[1,3]")
    private List<Integer> difficultyList;

    @ApiModelProperty("学科名")
    private List<String> subjectNameList;

    @ApiModelProperty(value = "年级水平[1.小学,2.初中,3.高中,4.大学]",allowableValues = "range[1,4]")
    private List<Integer> gradeLevelList;

}
