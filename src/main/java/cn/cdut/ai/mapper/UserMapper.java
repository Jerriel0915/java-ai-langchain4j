package cn.cdut.ai.mapper;

import cn.cdut.ai.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据Username查询用户信息
     * @param username 用户名
     * @return
     */
    User selectByUsername(String username);

}
