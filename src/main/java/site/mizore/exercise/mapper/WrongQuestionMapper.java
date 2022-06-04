package site.mizore.exercise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.mizore.exercise.domain.WrongQuestion;

@Mapper
public interface WrongQuestionMapper extends BaseMapper<WrongQuestion> {
}
