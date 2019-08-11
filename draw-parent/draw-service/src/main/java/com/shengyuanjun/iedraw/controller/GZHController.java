package com.shengyuanjun.iedraw.controller;


import com.shengyuanjun.iedraw.domain.Quotations;
import com.shengyuanjun.iedraw.service.IQuotationsService;
import com.shengyuanjun.iedraw.service.TokenService;
import com.shengyuanjun.iedraw.service.UserInfoService;
import com.shengyuanjun.iedraw.test.WXMsg.TestWX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * @program: participate
 * @description: 公众号Controller类
 * @author: gq544
 * @create: 2019-08-08 13:06
 */
@RestController
@PropertySource("classpath:constants/constant.properties")
@RequestMapping("/gzhs")
public class GZHController {

    private static final Logger logger = LoggerFactory.getLogger(GZHController.class);

    @Resource
    private IQuotationsService quotationsServiceImpl;
    //获取用户信息
    @Resource
    private UserInfoService userinfoServiceImpl;

    @Resource
    private TokenService tokenServiceImpl;


    @RequestMapping("/msgsend")
    public String senemsg(String openid){
        String accesstoken = tokenServiceImpl.getAccessTokenFromDBById(1).getAccesstoken();
        System.out.println("openid = "+openid);
        System.out.println("ctr  accesstoke = "+accesstoken);
        String value = TestWX.senMsgActive(openid, accesstoken);
        return value;
    }

    /**
    *@Description: 通过code，换取openid，对对应用户推送消息
    *@Param: [request]
    *@return: java.lang.String
    *@Author: gq544
    *@date: 2019/8/8 13:09
    */
    @RequestMapping("/lukptc")
    @ResponseBody
    public String GetGZHOpenid(HttpServletRequest request) throws IOException {
        logger.info("获取code并申请获得openid");
        String code = request.getParameter("code");//获取code
        String openid = userinfoServiceImpl.getOpenid(code);
        //公众号对此登录用户进行消息推送
        String accesstoken = tokenServiceImpl.getAccessTokenFromDBById(1).getAccesstoken();
        TestWX.senMsgActive(openid,accesstoken);
        return "获取的openid = " + openid + "并已经推送消息";
    }
/**
*@Description:直接通过openid，对对应用户推送消息
*@Param: [request]
*@return: java.lang.String
*@Author: gq544
*@date: 2019/8/8 13:09
*/
    @RequestMapping("/lukpto")
    @ResponseBody
    public String GetGZHOpenidToSendMSG(HttpServletRequest request) throws IOException {
        logger.info("获取openid并准备进行推送。。。");
        String openid = request.getParameter("openid");//获取code
        //公众号对此登录用户进行消息推送
        String accesstoken = tokenServiceImpl.getAccessTokenFromDBById(1).getAccesstoken();
        TestWX.senMsgActive(openid,accesstoken);
        return "获取的openid = " + openid + "并已经推送消息";
    }


    /**
     * 04
     *
     * @Description: 通过前段传递过来的状态返回对应语句 6条
     * @Param: [status]
     * @return: java.util.List<com.example.gzher.entitys.Quotations>
     * @Author: gq544
     * @date: 2019/8/9 1:43
     */
    @RequestMapping(value="/words",produces = "application/json;charset=UTF-8")
    public List<Quotations> backWords(String status) {
        Long statu = Long.parseLong(status);
        List<Quotations> qlist = quotationsServiceImpl.selectQuotationsByStatus(statu);
        System.out.println("value = " + qlist);

        List<Integer> indexList = new Vector<>();

        for (int i = 0; i < qlist.size(); i++) {
            indexList.add(i);
        }
        List<Quotations> list = new Vector<>();
        Random r = new Random();
        for (int j = 0; j < 6; j++) {
            int index = r.nextInt(indexList.size());
            list.add(qlist.get(indexList.get(index)));
            indexList.remove(index);
        }
        return list;
    }
}