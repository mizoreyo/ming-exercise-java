package site.mizore.exercise.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录参数
 */
@Data
public class AdminLoginParam {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

}
