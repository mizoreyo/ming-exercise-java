package site.mizore.exercise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.mizore.exercise.domain.Paper;
import site.mizore.exercise.domain.PaperQuestion;
import site.mizore.exercise.domain.Question;
import site.mizore.exercise.mapper.PaperMapper;
import site.mizore.exercise.mapper.QuestionMapper;
import site.mizore.exercise.service.PaperService;

@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperService {
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Integer scoring(Paper paper) {
        Integer score=0;
        for(PaperQuestion q : paper.getPaperContent().getSelectQuestions()) {
            Question qs=questionMapper.selectById(q.getQuestionId());
            q.setTrueAnswer(qs.getAnswer());
            if(q.getUserAnswer().equals(qs.getAnswer())) {
                q.setQuestionUserScore(q.getQuestionScore());
                score+=q.getQuestionUserScore();
            } else {
                q.setQuestionUserScore(0);
            }
        }
        for(PaperQuestion q : paper.getPaperContent().getMoreSelectQuestions()) {
            Question qs=questionMapper.selectById(q.getQuestionId());
            q.setTrueAnswer(qs.getAnswer());
            if(q.getUserAnswer().equals(qs.getAnswer())) {
                q.setQuestionUserScore(q.getQuestionScore());
                score+=q.getQuestionUserScore();
            } else {
                q.setQuestionUserScore(0);
            }
        }
        for(PaperQuestion q : paper.getPaperContent().getFillQuestions()) {
            Question qs=questionMapper.selectById(q.getQuestionId());
            q.setTrueAnswer(qs.getAnswer());
            if(q.getUserAnswer().equals(qs.getAnswer())) {
                q.setQuestionUserScore(q.getQuestionScore());
                score+=q.getQuestionUserScore();
            } else {
                q.setQuestionUserScore(0);
            }
        }
        for(PaperQuestion q : paper.getPaperContent().getJudgeQuestions()) {
            Question qs=questionMapper.selectById(q.getQuestionId());
            q.setTrueAnswer(qs.getAnswer());
            if(q.getUserAnswer().equals(qs.getAnswer())) {
                q.setQuestionUserScore(q.getQuestionScore());
                score+=q.getQuestionUserScore();
            } else {
                q.setQuestionUserScore(0);
            }
        }
        paper.setComplete(1);
        paper.setUserScore(score);
        baseMapper.updateById(paper);
        return score;
    }
}
