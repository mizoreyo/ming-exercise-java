package site.mizore.exercise.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.List;

@Data
@TableName(value="t_question",autoResultMap = true)
public class Question {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer questionType;
    private String questionInfo;
    private String answer;
    private String analysis;
    private Integer difficulty;
    private Long subjectId;
    private String subjectName;
    private Integer gradeLevel;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> options;

}
