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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.mizore.exercise.common.api.CommonResult;
import site.mizore.exercise.domain.Question;
import site.mizore.exercise.domain.WrongQuestion;
import site.mizore.exercise.dto.AdminUserDetails;
import site.mizore.exercise.dto.QuestionParam;
import site.mizore.exercise.dto.QuestionQueryParam;
import site.mizore.exercise.service.QuestionService;
import site.mizore.exercise.service.WrongQuestionService;

import java.util.List;
import java.util.Map;

@Api(tags = "QuestionController")
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private WrongQuestionService wrongQuestionService;

    @ApiOperation("根据id获取题目")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable("id") Long id) {
        Question question = questionService.getById(id);
        return CommonResult.success(question);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("增加题目")
    @PostMapping
    public CommonResult create(@RequestBody @Validated QuestionParam questionParam) {
        Question question=new Question();
        BeanUtils.copyProperties(questionParam,question);
        boolean result= questionService.save(question);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("根据id修改题目")
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable("id") Long id,
                               @RequestBody @Validated QuestionParam questionParam) {
        Question question=new Question();
        BeanUtils.copyProperties(questionParam,question);
        question.setId(id);
        boolean result = questionService.updateById(question);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("根据id删除题目")
    @DeleteMapping("/{id}")
    public CommonResult deleteItem(@PathVariable("id") Long id) {
        boolean result = questionService.removeById(id);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获取分页")
    @GetMapping("/page")
    public CommonResult getPage(@RequestParam(value = "p",defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "size",defaultValue = "10") Integer pageSize) {
        Page<Question> page=new Page<>(pageNum,pageSize);
        Page<Question> resultPage=questionService.page(page);
        return CommonResult.success(resultPage);
    }

    @ApiOperation("获取随机题目")
    @GetMapping("/random")
    public CommonResult randomItem(QuestionQueryParam questionParam) {
        System.out.println(questionParam);
        List<Question> question=questionService.randomItem(questionParam);
        return CommonResult.success(question);
    }

    @ApiOperation("验证答案")
    @PostMapping("/validate/{id}")
    public CommonResult validate(@PathVariable("id") Long id,
                                 @RequestBody Map<String,String> body) {
        Question question=questionService.getById(id);
        System.out.println(body.get("response"));
        if(question.getAnswer().equals(body.get("response"))) {
            return CommonResult.success(true,"答案正确");
        } else {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            AdminUserDetails userDetails= (AdminUserDetails) authentication.getPrincipal();
            WrongQuestion wrongQuestion=new WrongQuestion();
            wrongQuestion.setQuestionId(id);
            wrongQuestion.setQuestionName(question.getQuestionInfo());
            wrongQuestion.setUserId(userDetails.getId());
            wrongQuestion.setWrongAnswer(body.get("response"));
            wrongQuestionService.save(wrongQuestion);
            return CommonResult.failed("答案错误");
        }
    }

    @ApiOperation("批量获取题目")
    @GetMapping("/list")
    public CommonResult getList(@RequestParam List<Long> ids) {
        QueryWrapper<Question> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Question> questions= questionService.list(queryWrapper);
        return CommonResult.success(questions);
    }

}
