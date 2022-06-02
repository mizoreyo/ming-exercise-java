package site.mizore.exercise.service;

import site.mizore.exercise.domain.User;
import site.mizore.exercise.dto.AdminLoginParam;

public interface AdminService {

    /**
     * 登录功能
     * @param adminLoginParam 用户登录参数
     * @return 生成jwt的token
     */
    String login(AdminLoginParam adminLoginParam);

    /**
     * 注册功能
     * @param adminLoginParam 用户注册参数
     * @return
     */
    int register(AdminLoginParam adminLoginParam);

}
