package site.mizore.exercise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import site.mizore.exercise.domain.WrongQuestion;
import site.mizore.exercise.mapper.WrongQuestionMapper;
import site.mizore.exercise.service.WrongQuestionService;

@Service
public class WrongQuestionServiceImpl extends ServiceImpl<WrongQuestionMapper, WrongQuestion> implements WrongQuestionService {
}
