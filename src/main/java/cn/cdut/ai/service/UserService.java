package cn.cdut.ai.service;

import cn.cdut.ai.DTO.UserLoginDto;
import cn.cdut.ai.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    User login(UserLoginDto userLoginDto);

    void register(UserLoginDto userLoginDto);
}
