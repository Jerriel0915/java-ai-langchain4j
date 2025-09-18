package cn.cdut.ai.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 预约表
 */
@TableName("appointment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer lawyerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
