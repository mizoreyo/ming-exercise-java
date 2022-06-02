package site.mizore.exercise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import site.mizore.exercise.domain.Subject;
import site.mizore.exercise.mapper.SubjectMapper;
import site.mizore.exercise.service.SubjectService;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
}
