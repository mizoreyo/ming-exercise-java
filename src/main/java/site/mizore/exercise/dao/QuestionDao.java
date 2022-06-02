package site.mizore.exercise.dao;

import org.apache.ibatis.annotations.Mapper;
import site.mizore.exercise.domain.Question;
import site.mizore.exercise.dto.QuestionQueryParam;

import java.util.List;

@Mapper
public interface QuestionDao {

    List<Question> randomItem(QuestionQueryParam queryParam);

}
