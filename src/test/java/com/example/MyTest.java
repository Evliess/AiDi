package com.example;

import evl.io.constant.ServiceConstants;
import evl.io.utils.RestUtils;
import evl.io.utils.SlackUtils;

import java.io.IOException;

public class MyTest {

    //认证实现

    public static void main(String[] args) throws IOException {
        System.out.println("156111122221".matches(ServiceConstants.PHONE_PATTERN));
        String originalString = "yw";
        String password = "yw";

        System.out.println("原始字符串: " + originalString);
        System.out.println("密码: " + password);

        // 加密（自动生成salt）
        String encryptedString = RestUtils.encryptWithSalt(originalString, password);
        System.out.println("加密后的字符串: " + encryptedString);

        // 解密
        String decryptedString = RestUtils.decryptWithSalt(encryptedString, password);
        System.out.println("解密后的字符串: " + decryptedString);

        String temp = "a.b";
        System.out.println(temp.split("\\.")[0]);


        String url = "<WEBHOOK_URL>";

        SlackUtils slackUtils = new SlackUtils(url);
        slackUtils.sendMessage("", "", "");


    }
}
