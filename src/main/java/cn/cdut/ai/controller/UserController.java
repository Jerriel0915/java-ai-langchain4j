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
        userService.register(userLoginDto);
        return Result.success("注册成功");
    }


}
