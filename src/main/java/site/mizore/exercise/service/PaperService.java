package site.mizore.exercise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import site.mizore.exercise.domain.Paper;

public interface PaperService extends IService<Paper> {

    Integer scoring(Paper paper);

}
