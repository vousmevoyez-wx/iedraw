package com.shengyuanjun.iedraw.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shengyuanjun.iedraw.service.TokenService;
import com.shengyuanjun.iedraw.test.tokentest.NewTokenTester;
import com.shengyuanjun.iedraw.util.GetSignature;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


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


    private static final Logger logger = LoggerFactory.getLogger(PGController.class);

    @Value("${gzh.appid}")
    private String appid;



    @Resource
    private TokenService tokenServiceImpl;

    //获取signature
    @RequestMapping(value = "/sign",method = RequestMethod.GET)
    public String get(String url)
    {

        String accesstoken = NewTokenTester.getaccessToken();
        //GetSignature.getSignature(url,accesstoken);
        GetSignature.getSignatureFromMap(url,accesstoken);
        return "";
    }



    //获取signature
    @RequestMapping(value = "/signa",method = RequestMethod.GET)
    public String getSignature(String url)
    {
        //通过数据库数据获取accesstoken

        String accesstoken = NewTokenTester.getaccessToken();

        Map<String,String> signature = GetSignature.getSignature(appid,url,accesstoken);


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
