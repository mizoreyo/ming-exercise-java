package site.mizore.exercise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.mizore.exercise.domain.Paper;

@Mapper
public interface PaperMapper extends BaseMapper<Paper> {
}
