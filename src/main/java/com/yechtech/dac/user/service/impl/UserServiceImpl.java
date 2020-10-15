package com.yechtech.dac.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.utils.DateFormatUtil;
import com.yechtech.dac.user.dao.UserMapper;
import com.yechtech.dac.user.domain.User;
import com.yechtech.dac.user.dto.LoginDTO;
import com.yechtech.dac.user.dto.LoginTokenDto;
import com.yechtech.dac.user.dto.UserGetDto;
import com.yechtech.dac.user.dto.UserInfoAndPermissionDTO;
import com.yechtech.dac.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Map;

/**
 * @author James
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${spring.profiles.active}")
    private String profile;

    private final static String IDENTIFICATION="$MDAP";
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public LoginTokenDto getToken(UserGetDto userGetDto) {
       /*参数
       *    client_id
            client_secret
            redirect_uri
            code
            grant_type 授权类型，常量值：authorization_code
            oauth_timestamp  时间戳，格式示例：1555578236912
       * */
        // @RequestLine("GET /token?client_id={clientId}&client_secret={clientSecret}&grant_type={grantType}&redirect_uri={redirectUri}&code={code}&oauth_timestamp={oauthTimestamp}")
        //AppInfoProperties appInfo=new AppInfoProperties();
       // String accessToken=restTemplate.postForObject("https://ssom3rdoauthtest.hwwt2.com/sso4magicbox/oauth/token", appInfo,String.class);

        String accessToken="test";
        //拿着AccessToken拼接mdap去请求dap中的登录接口，获取token以及权限信息
        //{" + dapToken + "}
        String dapToken=accessToken+IDENTIFICATION;
        DacResponse dacResponse=null;
        LoginTokenDto loginTokenDto=new LoginTokenDto();
        if (profile.equals("dev")){
            dacResponse=restTemplate.postForObject("http://192.168.31.101/api/login", new LoginDTO("test","test"), DacResponse.class);
            Map<String,Object> userInfo= (Map<String, Object>) dacResponse.get("data");
            loginTokenDto.setAccessToken((String) userInfo.get("token"));
            loginTokenDto.setFullName((String)userInfo.get("userFullName"));
        }else if (profile.equals("prd")){
            dacResponse= restTemplate.getForObject("http://localhost:8080/api/user/info/{dapToken}", DacResponse.class,dapToken);
            UserInfoAndPermissionDTO res= (UserInfoAndPermissionDTO) dacResponse.get("data");
            //封装数据返回   accessToken  fullName
            loginTokenDto.setAccessToken(res.getToken());
            loginTokenDto.setFullName(res.getYumLocalName());
        }
        //封装数据返回   accessToken  fullName  lastLoginTime
        String lastLoginTime = DateFormatUtil.DateToString(Calendar.getInstance().getTime(), "YYYYMMddHHmmss");
        loginTokenDto.setLastLoginTime(lastLoginTime);
        return loginTokenDto;
    }
}
