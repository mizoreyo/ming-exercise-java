package site.mizore.exercise.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

@Data
@TableName(value="t_paper",autoResultMap = true)
public class Paper {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long subjectId;
    private String subjectName;
    private Integer gradeLevel;
    private Long userId;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private PaperContent paperContent;
    private Integer complete;
    private Integer userScore;

}
