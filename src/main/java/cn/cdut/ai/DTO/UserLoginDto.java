package cn.cdut.ai.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工登录时传递的数据类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
