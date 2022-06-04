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
import site.mizore.exercise.domain.Paper;
import site.mizore.exercise.domain.PaperContent;
import site.mizore.exercise.domain.PaperQuestion;
import site.mizore.exercise.domain.Question;
import site.mizore.exercise.dto.AdminUserDetails;
import site.mizore.exercise.dto.CreatePaperParam;
import site.mizore.exercise.dto.PaperParam;
import site.mizore.exercise.dto.QuestionQueryParam;
import site.mizore.exercise.service.PaperService;
import site.mizore.exercise.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "PaperController")
@RestController
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private QuestionService questionService;
    
    @ApiOperation("获取用户未完成的试卷")
    @GetMapping
    public CommonResult getOwnUncompletedPaper() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails userDetails= (AdminUserDetails) authentication.getPrincipal();
        QueryWrapper<Paper> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("complete",0);
        queryWrapper.eq("user_id",userDetails.getId());
        Paper paper=paperService.getOne(queryWrapper);
        return CommonResult.success(paper);
    }

    @ApiOperation("新建试卷")
    @PostMapping
    public CommonResult createOwnPaper(@RequestBody CreatePaperParam createPaperParam) {
        // 设置学科和年级查询条件
        List<String> subjectNameList=new ArrayList<>();
        List<Integer> gradeLevelList=new ArrayList<>();
        subjectNameList.add(createPaperParam.getSubjectName());
        gradeLevelList.add(createPaperParam.getGradeLevel());
        QuestionQueryParam queryParam=new QuestionQueryParam();
        queryParam.setSubjectNameList(subjectNameList);
        queryParam.setGradeLevelList(gradeLevelList);

        List<Integer> questionTypeList=new ArrayList<>();
        questionTypeList.add(0);
        queryParam.setQuestionTypeList(questionTypeList);

        //查询选择题
        queryParam.setLimit(10);
        questionTypeList.set(0,1);
        List<Question> selectQuestions= questionService.randomItem(queryParam);
        //查询多选题
        queryParam.setLimit(5);
        questionTypeList.set(0,2);
        List<Question> moreSelectQuestions=questionService.randomItem(queryParam);
        //查询填空题
        queryParam.setLimit(10);
        questionTypeList.set(0,3);
        List<Question> fillQuestions=questionService.randomItem(queryParam);
        //查询判断题
        queryParam.setLimit(10);
        questionTypeList.set(0,4);
        List<Question> judgeQuestions=questionService.randomItem(queryParam);
        PaperContent paperContent=new PaperContent();
        paperContent.setSelectQuestions(PaperQuestion.convertList(selectQuestions,2));
        paperContent.setMoreSelectQuestions(PaperQuestion.convertList(moreSelectQuestions,6));
        paperContent.setFillQuestions(PaperQuestion.convertList(fillQuestions,3));
        paperContent.setJudgeQuestions(PaperQuestion.convertList(judgeQuestions,2));

        Paper paper=new Paper();
        paper.setPaperContent(paperContent);
        paper.setSubjectName(createPaperParam.getSubjectName());
        paper.setGradeLevel(createPaperParam.getGradeLevel());
        paper.setName(createPaperParam.getPaperName());
        paper.setComplete(0);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails userDetails= (AdminUserDetails) authentication.getPrincipal();
        paper.setUserId(userDetails.getId());

        boolean result=paperService.save(paper);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批改试卷")
    @PostMapping("/scoring")
    public CommonResult scoring(@RequestBody Paper paper) {
        Integer score= paperService.scoring(paper);
        return CommonResult.success(score);
    }

    @ApiOperation("获取已完成的试卷page")
    @GetMapping("/uncomp/page")
    public CommonResult getUncompPage(@RequestParam(value = "p",defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "size",defaultValue = "10") Integer pageSize) {
        Page<Paper> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Paper> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("complete",1);
        Page result=paperService.page(page,queryWrapper);
        return CommonResult.success(result);
    }

    @ApiOperation("根据id获取试卷")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable("id") Long id) {
        Paper paper= paperService.getById(id);
        return CommonResult.success(paper);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("删除试卷")
    @DeleteMapping("/{id}")
    public CommonResult deleteItem(@PathVariable("id") Long id) {
        boolean result=paperService.removeById(id);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("修改试卷")
    @PutMapping("/{id}")
    public CommonResult updateItem(@PathVariable("id") Long id,
                                   @RequestBody PaperParam paperParam) {
        Paper paper=new Paper();
        BeanUtils.copyProperties(paperParam,paper);
        paper.setId(id);
        boolean result=paperService.updateById(paper);
        if(result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获取试卷分页")
    @GetMapping("/page")
    public CommonResult getPage(@RequestParam(value = "p",defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "size",defaultValue = "10") Integer pageSize) {
        Page<Paper> page=new Page<>(pageNum,pageSize);
        Page<Paper> result=paperService.page(page);
        return CommonResult.success(result);
    }

}
