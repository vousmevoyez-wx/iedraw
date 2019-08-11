package com.shengyuanjun.iedraw.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shengyuanjun.iedraw.mapper.AccessTokenMapper;
import com.shengyuanjun.iedraw.util.GetSignature;
import com.shengyuanjun.iedraw.util.SHA1Util;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

import static com.netflix.config.DeploymentContext.ContextKey.appId;

/**
 * @author Admin
 * @version V1.0
 * @className GetSignatureController
 * @description TODO
 * @date 2019/8/9
 **/
@RestController
@RequestMapping("/signature")
@PropertySource("classpath:constants/constant.properties")
public class GetSignatureController {


    @Value("${gzh.appid}")
    private String appid;



    @Resource
    private AccessTokenMapper accessTokenMapper;

    //获取signature
    @RequestMapping(value = "/sign",method = RequestMethod.GET)
    public String get(String url)
    {

        String accesstoken = accessTokenMapper.selectByPrimaryKey(1).getAccesstoken();
        //GetSignature.getSignature(url,accesstoken);
        GetSignature.getSignatureFromMap(url,accesstoken);
        return "";
    }

    //获取signature
    @RequestMapping(value = "/signa",method = RequestMethod.GET)
    public String getSignature(String url)
    {
        System.out.println("前端传递过来的坐标为： "+url);
        String accesstoken = accessTokenMapper.selectByPrimaryKey(1).getAccesstoken();
        Map<String,String> signature = GetSignature.getSignature(url,accesstoken);

        //Map<String,String> map =  GetSignature.getSignatureFromMap(url,accesstoken);

        //String appid = signature.get("appid");
        JSONObject jsonObject = JSONObject.fromObject(signature);
//        Object[] keys = signature.keySet().toArray();
//        Object[] values = signature.values().toArray();
//        System.out.println("key数组:"+ Arrays.toString(keys));
//        System.out.println("value数组:"+Arrays.toString(values));
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//
//        }
        System.out.println(jsonObject);


        //AjaxResult ajaxResult = new AjaxResult();

       //ajaxResult.setRetObj());
        String value = MapAndJson(signature);
       System.out.println("value = " + value);
        return  value;
        /*System.out.println("map11111 = " + map);
        String nonceStr = "nonceStr = "+ map.get("nonceStr");
        System.out.println("nonceStr = "+nonceStr);
        Map value = SHA1Util.checkSignature(map.get("jsapi_ticket"),map.get("nonceStr"),map.get("timestamp"),map.get("url"));
        System.out.println("value = " + value);
        value.put("appid",appid);
        return value;*/
    }


    public String MapAndJson(Map<String,String> map) {

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(map);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

}
