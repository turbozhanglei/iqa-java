package com.yechtech.dac.user.controller;


import com.yechtech.dac.common.controller.BaseController;
import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.utils.CheckSignatureUtil;
import com.yechtech.dac.user.dto.LoginTokenDto;
import com.yechtech.dac.user.dto.UserGetDto;
import com.yechtech.dac.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author James
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;


    @GetMapping("/token")
    public DacResponse userToken(UserGetDto userGetDto) throws UnsupportedEncodingException {

        if (!CheckSignatureUtil.check(userGetDto.getCode(),userGetDto.getTimestamp(),userGetDto.getSignature())){
            //验签失败，参数有问题
            return new DacResponse().message("验签失败，参数异常");
        }
        LoginTokenDto loginTokenDto=userService.getToken(userGetDto);

        return new DacResponse().data(loginTokenDto);
    }

}
