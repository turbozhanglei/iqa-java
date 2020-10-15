package com.yechtech.dac.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author James
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
