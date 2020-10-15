package com.yechtech.dac.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yechtech.dac.user.domain.User;
import com.yechtech.dac.user.dto.LoginTokenDto;
import com.yechtech.dac.user.dto.UserGetDto;

/**
 * @author James
 */
public interface UserService extends IService<User> {

    LoginTokenDto getToken(UserGetDto userGetDto);
}
