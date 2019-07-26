package com.shengyuanjun.iedraw;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrUtils {
    /**
     * 把逗号分隔的字符串转换字符串数组
     *
     * @param str
     * @return
     */
    public static String[] splitStr2StrArr(String str,String reg) {
        if (str != null && !str.equals("")) {
            return str.split(reg);
        }
        return null;
    }


    /**
     * 把逗号分隔字符串转换List的Long
     *
     * @param str
     * @return
     */
    public static List<Long> splitStr2LongArr(String str) {
        return splitStr2LongArr(str,",");
    }

    /**
     * 通用的分割
     * @param str
     * @param reg
     * @return
     */
    public static List<Long> splitStr2LongArr(String str,String reg){
        String[] strings = splitStr2StrArr(str,reg);
        List<Long> list = new ArrayList<>();

        for (String string : strings) {
            if(string!=null&&!"".equals(string)){
                Long item = Long.parseLong(string);
                list.add(item);
            }
        }
        return list;
    }


    public static String getRandomString(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();

    }

    public static String getComplexRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String convertPropertiesToHtml(String properties){
        StringBuilder sBuilder = new StringBuilder();
        String[] propArr = properties.split("_");
        for (String props : propArr) {
            String[] valueArr = props.split(":");
            sBuilder.append(valueArr[1]).append(":").append(valueArr[3]).append("<br>");
        }
        return sBuilder.toString();
    }

}
