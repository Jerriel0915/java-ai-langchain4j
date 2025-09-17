package cn.cdut.ai.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**Users类是和数据表进行映射的*/


@TableName("users")
@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
}
