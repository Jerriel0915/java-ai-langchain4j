package cn.cdut.ai.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**Users类是和数据表进行映射的*/

@TableName("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(value = "id", type = IdType.AUTO)  // 明确指定主键和自增策略
    private Integer id;
    private String username;
    private String password;
}
