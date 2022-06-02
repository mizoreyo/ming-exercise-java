package site.mizore.exercise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import site.mizore.exercise.domain.Question;
import site.mizore.exercise.dto.QuestionQueryParam;

import java.util.List;

public interface QuestionService extends IService<Question> {

     List<Question> randomItem(QuestionQueryParam queryParam);

}
