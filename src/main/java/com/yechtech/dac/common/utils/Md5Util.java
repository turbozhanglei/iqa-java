package com.yechtech.dac.common.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author ChenYu
 * @create 2020-09-11
 * @tag I love java better than girl
 */
public class Md5Util {


    public static String md5(String str) throws UnsupportedEncodingException {

        //byte[] secret = DigestUtils.md5Digest(str.getBytes());

        // return new String(secret,"UTF-8");
        String secret = DigestUtils.md5DigestAsHex(str.getBytes());
        return secret;
    }
}
