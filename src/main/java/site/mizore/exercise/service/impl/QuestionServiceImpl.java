package site.mizore.exercise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.mizore.exercise.dao.QuestionDao;
import site.mizore.exercise.domain.Question;
import site.mizore.exercise.dto.QuestionQueryParam;
import site.mizore.exercise.mapper.QuestionMapper;
import site.mizore.exercise.service.QuestionService;

import java.util.List;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public List<Question> randomItem(QuestionQueryParam queryParam) {

        return questionDao.randomItem(queryParam);

    }

}
