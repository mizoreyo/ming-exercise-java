package site.mizore.exercise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import site.mizore.exercise.domain.User;
import site.mizore.exercise.mapper.UserMapper;
import site.mizore.exercise.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
