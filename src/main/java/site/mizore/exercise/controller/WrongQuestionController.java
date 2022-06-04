package site.mizore.exercise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import site.mizore.exercise.common.api.CommonResult;
import site.mizore.exercise.domain.WrongQuestion;
import site.mizore.exercise.dto.AdminUserDetails;
import site.mizore.exercise.dto.WrongQuestionParam;
import site.mizore.exercise.service.WrongQuestionService;

@Api(tags = "WrongQuestionController")
@RestController
@RequestMapping("/wrong")
public class WrongQuestionController {
    @Autowired
    private WrongQuestionService wrongQuestionService;

    @ApiOperation("增加错题")
    @PostMapping
    public CommonResult create(@RequestBody WrongQuestionParam wrongQuestionParam) {
        WrongQuestion wrongQuestion=new WrongQuestion();
        BeanUtils.copyProperties(wrongQuestionParam,wrongQuestion);
        boolean result=wrongQuestionService.save(wrongQuestion);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("删除错题")
    @DeleteMapping("/{id}")
    public CommonResult deleteItem(@PathVariable("id") Long id) {
        boolean result= wrongQuestionService.removeById(id);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("修改错题")
    @PutMapping("/{id}")
    public CommonResult updateItem(@PathVariable("id") Long id,
                                   @RequestBody WrongQuestionParam wrongQuestionParam) {
        WrongQuestion wrongQuestion=new WrongQuestion();
        BeanUtils.copyProperties(wrongQuestionParam,wrongQuestion);
        wrongQuestion.setId(id);
        boolean result=wrongQuestionService.updateById(wrongQuestion);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获取错题")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable("id") Long id) {
        WrongQuestion wrongQuestion=wrongQuestionService.getById(id);
        return CommonResult.success(wrongQuestion);
    }

    @ApiOperation("获取错题page")
    @GetMapping("/page")
    public CommonResult getPage(@RequestParam(value = "p",defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "size",defaultValue = "10") Integer pageSize) {
        Page<WrongQuestion> page=new Page<>(pageNum,pageSize);
        Page<WrongQuestion> result=wrongQuestionService.page(page);
        return CommonResult.success(result);
    }

    @ApiOperation("用户获取自己错题page")
    @GetMapping("/own/page")
    public CommonResult getOwnPage(@RequestParam(value = "p",defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "size",defaultValue = "10") Integer pageSize) {
        Page<WrongQuestion> page=new Page<>(pageNum,pageSize);
        QueryWrapper<WrongQuestion> queryWrapper=new QueryWrapper<>();
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails userDetails= (AdminUserDetails) authentication.getPrincipal();
        queryWrapper.eq("user_id",userDetails.getId());
        Page<WrongQuestion> result=wrongQuestionService.page(page,queryWrapper);
        return CommonResult.success(result);
    }
}
