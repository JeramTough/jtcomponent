package com.jeramtough.jtcomponent.utils.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2018-09-12 21:05
 * by @author JeramTough
 */
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))" +
            "*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^((13[0-9])|(14[5,7,9])" +
            "|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$");


    public static boolean isEmail(String email) {
        //正则表达式的匹配器
        Matcher m = EMAIL_PATTERN.matcher(email);
        //进行正则匹配
        return m.matches();
    }


    public static boolean isPhone(String phone) {
        if (phone.length() != 11) {
            return false;
        }
        else {
            Matcher m = PHONE_NUMBER_PATTERN.matcher(phone);
            return m.matches();
        }

    }
}
