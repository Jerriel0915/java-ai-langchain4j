package cn.cdut.ai.AITools;

import cn.cdut.ai.mapper.AppointmentMapper;
import cn.cdut.ai.mapper.LawyerMapper;
import cn.cdut.ai.model.Appointment;
import cn.cdut.ai.model.Lawyer;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentTools {

    private final LawyerMapper lawyerMapper;
    private final AppointmentMapper appointmentMapper;

    @Tool("获取所有律师的列表")
    public List<Lawyer> getLawyerInfo() {
        return lawyerMapper.selectAll();
    }

    @Tool("为客户创建律师预约")
    public String createAppointment(
            @P("律师的ID") Integer lawyerId,
            @P("预约的开始时间，格式为 yyyy-MM-dd HH:mm:ss") String startTime,
            @P("预约的结束时间，格式为 yyyy-MM-dd HH:mm:ss") String endTime) {
        Appointment appointment = new Appointment();
        appointment.setLawyerId(lawyerId);
        appointment.setStartTime(LocalDateTime.parse(startTime.replace(" ", "T")));
        appointment.setEndTime(LocalDateTime.parse(endTime.replace(" ", "T")));
        // In a real application, you would get the user ID from the security context.
        // Here we'll just use a placeholder.
        appointment.setUserId(1);

        appointmentMapper.insert(appointment);

        return "预约创建成功，预约ID为：" + appointment.getId();
    }
}
