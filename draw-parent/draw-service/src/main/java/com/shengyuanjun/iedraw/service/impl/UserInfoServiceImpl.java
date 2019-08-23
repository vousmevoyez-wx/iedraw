package com.shengyuanjun.iedraw.service.impl;


import com.shengyuanjun.iedraw.domain.UserInfo;
import com.shengyuanjun.iedraw.mapper.UserInfoMapper;
import com.shengyuanjun.iedraw.service.UserInfoService;
import com.shengyuanjun.iedraw.util.HttpGetUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: isfollow
 * @description: 用户信息获取
 * @author: gq544
 * @create: 2019-08-04 10:03
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Resource
    private UserInfoMapper userinfoMapper;


    @Value("${gzh.appsecret}")
    private String appsecret;

    @Value("${gzh.appid}")
    private String appid;


    /**
     *@Description: 通过code参数获取用户的openid
     *@Param: [code]
     *@return: java.lang.String
     *@Author: gq544
     *@date: 2019/8/5 18:27
     */
    public Map getOpenid(String code){
        Map map = new HashMap();
        Map params = new HashMap();
        params.put("appid", appid);
        params.put("secret", appsecret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        String result = HttpGetUtil.httpRequestToString(
                "https://api.weixin.qq.com/sns/oauth2/access_token",params);
        System.out.println("result000 = " + result);
        JSONObject jsonObject = JSONObject.fromObject(result);
        log.info("UserInfoServiceImpl   getOpenid  jsonObject = " +  jsonObject);
        /*if(jsonObject.get("openid").toString()!=null){
            String openid = jsonObject.get("openid").toString();
            return openid;
        }else{
            return "fail";
        }*/
        if(jsonObject.toString().contains("openid")){
            String openid = jsonObject.get("openid").toString();
            String accesstoken = jsonObject.get("access_token").toString();
            map.put("openid",openid);
            map.put("accesstoken",accesstoken);
            map.put("statues",1);
            map.put("value",result);
        }else{
            map.put("statues",-1);
            map.put("value",result);
        }
        return map;
    }

    /**
     *@Description: 判断是否为已关注的用户
     *@Param: [token, openid]
     *@return: boolean
     *@Author: gq544
     *@date: 2019/8/5 18:26
     */
    public boolean judgeIsFollow(String token,String openid){
        Integer subscribe = null;
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openid+"&lang=zh_CN";

        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            System.out.println("JSON字符串："+demoJson);
            subscribe = demoJson.getInt("subscribe");
            System.out.println("openid = "+demoJson.getString("openid"));
            is.close();
        } catch (Exception e) {
            log.info("认证信息有误。。认证失败。。");
            return false;
        }
        return 1==subscribe?true:false;
    }

    /**
     *@Description: 通过用户openid和token获取用户基本信息
     *@Param: [token, openid]
     *@return: net.sf.json.JSONObject
     *@Author: gq544
     *@date: 2019/8/5 18:26
     */
    public JSONObject getSNSUserInfo(String token,String openid){
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openid+"&lang=zh_CN";
        JSONObject demoJson = new JSONObject();
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            demoJson = JSONObject.fromObject(message);
            is.close();
        } catch (Exception e) {
            log.info("认证信息有误。。认证失败。。");
        }
        log.info("认证信息获取完成。。");
        System.out.println(demoJson);


        return demoJson;
    }



    //库中没有对应用户信息的时候用来保存用户资料信息
    public int saveUserInfomsg(UserInfo user){

        System.out.println("第一次进来的用户保存用户信息");

        return userinfoMapper.insertSelective(user);
    }

    @Override
    public UserInfo selectThisUser(String openid) {
        System.out.println("openid = "+openid);
        return userinfoMapper.selectUsermsgByOpenid(openid);
    }

    @Override
    public UserInfo selectUserById(Long id) {

        return userinfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateById(UserInfo backuser) {
        return userinfoMapper.updateByPrimaryKeySelective(backuser);
    }


}