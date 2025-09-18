package cn.cdut.ai.controller;

import cn.cdut.ai.DTO.UserLoginDto;
import cn.cdut.ai.Result.Result;
import cn.cdut.ai.Utils.BCryptUtil;
import cn.cdut.ai.Utils.JwtUtils;
import cn.cdut.ai.VO.UserLoginVo;
import cn.cdut.ai.constant.JwtClaimsConstant;
import cn.cdut.ai.model.User;
import cn.cdut.ai.properties.JwtProperties;
import cn.cdut.ai.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    Result<UserLoginVo> UserLogin(@RequestBody UserLoginDto userLoginDto) {
        log.info("userLogin:{}", userLoginDto);

        User user = userService.login(userLoginDto);

        // 成功登录后生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ID, user.getId());
        String token = JwtUtils.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims
        );

        return Result.success(UserLoginVo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build()
        );
    }

    /**
     * 用户注册接口
     * @param userLoginDto 用户注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserLoginDto userLoginDto) {
        log.info("userRegister:{}", userLoginDto);

        try {
            // 参数校验
            if (userLoginDto == null ||
                    userLoginDto.getUsername() == null ||
                    userLoginDto.getPassword() == null) {
                return Result.error("用户名和密码不能为空");
            }

            // 检查用户名是否已存在
            User existingUser = userService.getOne(
                    new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<User>()
                            .eq("username", userLoginDto.getUsername())
            );

            if (existingUser != null) {
                log.warn("用户名已存在: {}", userLoginDto.getUsername());
                return Result.error("用户名已存在");
            }

            // 创建新用户（让数据库自动生成ID，不需要手动设置）
            User newUser = new User();
            newUser.setUsername(userLoginDto.getUsername());
            // 使用BCrypt对密码进行加密
            String encodedPassword = BCryptUtil.encode(userLoginDto.getPassword());
            newUser.setPassword(encodedPassword);

            log.info("准备保存新用户: {}", newUser.getUsername());

            // 保存用户到数据库
            boolean saved = userService.save(newUser);

            log.info("用户保存结果: {}", saved);

            if (saved) {
                return Result.success("注册成功");
            } else {
                return Result.error("注册失败");
            }
        } catch (Exception e) {
            log.error("注册过程中发生异常: ", e);
            return Result.error("注册失败: " + e.getMessage());
        }
    }


}
