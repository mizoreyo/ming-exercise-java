package site.mizore.exercise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.mizore.exercise.common.utils.JwtTokenUtil;
import site.mizore.exercise.domain.RoleEnum;
import site.mizore.exercise.domain.User;
import site.mizore.exercise.dto.AdminLoginParam;
import site.mizore.exercise.mapper.UserMapper;
import site.mizore.exercise.service.AdminService;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String login(AdminLoginParam adminLoginParam) {
        String token=null;
        try{
            UserDetails userDetails=userDetailsService.loadUserByUsername(adminLoginParam.getUsername());
            if(!passwordEncoder.matches(adminLoginParam.getPassword(),userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token=jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException exception) {
            log.warn("登录异常:{}",exception.getMessage());
        }
        return token;
    }

    @Override
    public int register(AdminLoginParam adminLoginParam) {
        //查询是否有重名用户
        QueryWrapper<User> wrapper=new QueryWrapper<User>();
        wrapper.eq("username",adminLoginParam.getUsername());
        User user=userMapper.selectOne(wrapper);
        if(user!=null) {
            return 0;
        }
        user=new User();
        BeanUtils.copyProperties(adminLoginParam,user);
        //新注册用户均为普通用户
        user.setRole(RoleEnum.USER.getCode());
        //对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.insert(user);
    }
}
