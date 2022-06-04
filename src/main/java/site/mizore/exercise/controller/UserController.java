package site.mizore.exercise.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import site.mizore.exercise.common.api.CommonResult;
import site.mizore.exercise.domain.User;
import site.mizore.exercise.dto.UserParam;
import site.mizore.exercise.service.UserService;

@Api(tags = "UserController")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("获取用户分页")
    @GetMapping("/page")
    public CommonResult getPage(@RequestParam(value = "p",defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "size",defaultValue = "10") Integer pageSize) {
        Page<User> page=new Page<>(pageNum,pageSize);
        Page<User> result= userService.page(page);
        return CommonResult.success(result);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("增加用户")
    @PostMapping
    public CommonResult create(@RequestBody UserParam userParam) {
        User user=new User();
        BeanUtils.copyProperties(userParam,user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean result=userService.save(user);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public CommonResult deleteItem(@PathVariable("id") Long id) {
        boolean result=userService.removeById(id);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("更新用户")
    @PutMapping("/{id}")
    public CommonResult updateItem(@PathVariable("id") Long id,
                                   @RequestBody UserParam userParam) {
        User user=new User();
        BeanUtils.copyProperties(userParam,user);
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean result=userService.updateById(user);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获取指定用户")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable("id") Long id) {
        User user=userService.getById(id);
        return CommonResult.success(user);
    }

}
