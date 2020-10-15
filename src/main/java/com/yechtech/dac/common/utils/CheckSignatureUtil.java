package com.yechtech.dac.common.utils;

import java.io.UnsupportedEncodingException;

/**
 * @author ChenYu
 * @create 2020-09-16
 * @tag I love java better than girl
 * 访问接口时验证签名
 */
public class CheckSignatureUtil {
    public static boolean check(String code,String timeStamp,String signature) throws UnsupportedEncodingException {
        StringBuilder str=new StringBuilder();
        String secret = Md5Util.md5(str.append(code).append(timeStamp).append("mdap").toString());
        if (!secret.equals(signature)){
            //验签失败，参数有问题
            return false;
        }
        return true;
    }

}
