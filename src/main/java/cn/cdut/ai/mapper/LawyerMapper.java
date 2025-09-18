package cn.cdut.ai.mapper;

import cn.cdut.ai.model.Lawyer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LawyerMapper extends BaseMapper<Lawyer> {
    /**
     * 查询所有律师
     * @return
     */
    List<Lawyer> selectAll();
}
