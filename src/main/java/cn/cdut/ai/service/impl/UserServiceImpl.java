package cn.cdut.ai.service.impl;

import cn.cdut.ai.DTO.UserLoginDto;
import cn.cdut.ai.Utils.BCryptUtil;
import cn.cdut.ai.constant.MessageConstant;
import cn.cdut.ai.exception.AccountNotFoundException;
import cn.cdut.ai.exception.BaseException;
import cn.cdut.ai.exception.PasswordErrorException;
import cn.cdut.ai.exception.UserAlreadyExistsException;
import cn.cdut.ai.mapper.UserMapper;
import cn.cdut.ai.model.User;
import cn.cdut.ai.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        // 根据用户名查找用户信息
        User user = userMapper.selectByUsername(username);

        if (user == null) {
            // 账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        if (!BCryptUtil.matches(password, user.getPassword())) {
            // 密码比对错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        return user;
    }

    @Override
    public void register(UserLoginDto userLoginDto) {
        // 参数校验
        if (userLoginDto == null || userLoginDto.getUsername() == null || userLoginDto.getPassword() == null) {
            throw new BaseException("用户名和密码不能为空");
        }

        // 检查用户名是否已存在
        User existingUser = this.getOne(
                new QueryWrapper<User>().eq("username", userLoginDto.getUsername())
        );

        if (existingUser != null) {
            throw new UserAlreadyExistsException("用户名已存在");
        }

        // 创建新用户
        User newUser = new User();
        newUser.setUsername(userLoginDto.getUsername());
        // 使用BCrypt对密码进行加密
        String encodedPassword = BCryptUtil.encode(userLoginDto.getPassword());
        newUser.setPassword(encodedPassword);

        // 保存用户到数据库
        boolean saved = this.save(newUser);

        if (!saved) {
            throw new BaseException("注册失败");
        }
    }
}
