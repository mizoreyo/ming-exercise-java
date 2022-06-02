package site.mizore.exercise.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SubjectParam {

    @NotEmpty
    @ApiModelProperty(value = "学科名")
    private String name;
    @ApiModelProperty(value = "年级水平[1.小学,2.初中,3.高中,4.大学]",allowableValues = "range[1,4]")
    private Integer gradeLevel;

}
