package site.mizore.exercise.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.mizore.exercise.common.api.CommonResult;
import site.mizore.exercise.dto.AdminLoginParam;
import site.mizore.exercise.service.AdminService;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录管理Controller
 */
@Api(tags = "AdminController")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    @ApiOperation("登录以后返回token")
    public CommonResult login(@RequestBody @Validated AdminLoginParam adminLoginParam) {
        String token= adminService.login(adminLoginParam);
        if(token==null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String,String> data=new HashMap<>();
        data.put("tokenHead",tokenHead);
        data.put("token",token);
        return CommonResult.success(data);
    }

    @PostMapping("/register")
    @ApiOperation("注册功能")
    public CommonResult register(@RequestBody @Validated AdminLoginParam adminLoginParam) {
        if(adminLoginParam.getUsername()==null||
           adminLoginParam.getPassword()==null) {
            return CommonResult.failed("注册信息不全");
        }
        int count=adminService.register(adminLoginParam);
        if(count>0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed("用户已存在");
        }
    }

    @PostMapping("/validate")
    @ApiOperation("验证token")
    public CommonResult validate() {
        return CommonResult.success(true);
    }

}
