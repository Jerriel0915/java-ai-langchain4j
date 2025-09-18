package cn.cdut.ai.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 律师表
 */
@TableName("lawyer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lawyer {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String lawyerName;
    private Character gender;
    private String phone;
    private String statements;
}
