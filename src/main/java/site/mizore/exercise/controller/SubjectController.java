package site.mizore.exercise.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.mizore.exercise.common.api.CommonResult;
import site.mizore.exercise.domain.Subject;
import site.mizore.exercise.dto.SubjectParam;
import site.mizore.exercise.service.SubjectService;

@Api(tags = "SubjectController")
@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation("根据id获取学科")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable("id") Long id ) {
        Subject subject = subjectService.getById(id);
        return CommonResult.success(subject);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("增加学科")
    @PostMapping
    public CommonResult create(@RequestBody @Validated SubjectParam subjectParam) {
        Subject subject=new Subject();
        BeanUtils.copyProperties(subjectParam,subject);
        boolean result=subjectService.save(subject);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("根据id修改学科")
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable("id") Long id,
            @RequestBody @Validated SubjectParam subjectParam) {
        Subject subject=new Subject();
        BeanUtils.copyProperties(subjectParam,subject);
        subject.setId(id);
        boolean result=subjectService.updateById(subject);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("根据id删除学科")
    @DeleteMapping("/{id}")
    public CommonResult deleteItem(@PathVariable("id") Long id) {
        boolean result=subjectService.removeById(id);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("查询学科分页")
    @GetMapping("/page")
    public CommonResult getPage(@RequestParam(value = "p",defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "size",defaultValue = "10") Integer pageSize) {
        Page<Subject> subjectPage=new Page<>(pageNum,pageSize);
        Page<Subject> resultPage=subjectService.page(subjectPage,null);
        return CommonResult.success(resultPage);
    }

}
