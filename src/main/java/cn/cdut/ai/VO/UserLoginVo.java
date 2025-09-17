package cn.cdut.ai.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo {
    /**
     * 唯一id值
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;

    /**
     * jwt令牌
     */
    private String token;
}
