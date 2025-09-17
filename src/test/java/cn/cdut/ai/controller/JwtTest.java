package cn.cdut.ai.controller;

import cn.cdut.ai.Utils.JwtUtils;
import cn.cdut.ai.constant.JwtClaimsConstant;
import cn.cdut.ai.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class JwtTest implements HandlerInterceptor {
    @Autowired
    JwtProperties jwtProperties;

    /**
     * jwt 拦截校验
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截Controller方法
        if (!(handler instanceof HandlerMethod)) {
            // 所拦截不是动态方法，放行
            return true;
        }

        String token = request.getHeader(jwtProperties.getUserTokenName());

        try {
            log.info("开始Jwt校验：{}", token);
            Claims claims = JwtUtils.parseJWT(jwtProperties.getUserSecretKey(), token);
            Integer userId = Integer.valueOf(claims.get(JwtClaimsConstant.ID).toString());

            log.info("当前用户Id：{}", userId);

            //TODO 将当前用户ID存入线程空间

            return true;

        } catch (Exception e) {
            // 返回401未授权
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
