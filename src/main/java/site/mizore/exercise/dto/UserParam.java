package site.mizore.exercise.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class UserParam {

    private String username;
    private String password;
    private Integer gradeLevel;
    private Integer role;
    private String avatarPath;

}
