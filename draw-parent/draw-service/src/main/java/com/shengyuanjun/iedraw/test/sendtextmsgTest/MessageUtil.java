package com.shengyuanjun.iedraw.test.sendtextmsgTest;


import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MessageUtil {
    public static void main(String[] args) {
        String value = initNewsMessage("oG8vqvge36PK1G54J0pjsCLubfSM","wxa488859df53ffa43");
        System.out.println("value = " + value);
    }
    /**
     * 初始化图文消息
     */
    public static String initNewsMessage(String toUSerName, String fromUserName) {
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();
        //组建一条图文↓ ↓ ↓
        News newsItem = new News();
        newsItem.setTitle("欢迎来到智博会“江小白”展厅，您已获得一次抽奖机会，点击下方链接即可参与抽奖");
        //newsItem.setPicUrl("http://120.78.145.73:9001/services/draw/gop/KPPQJP1566138735023.png");
        //newsItem.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + "wxa488859df53ffa43" + "&redirect_uri=" + WXConstants.BASE_SERVER + "/wxuser/toRegister&response_type=code&scope=snsapi_base&state=BINDFACE#wechat_redirect");
        newsItem.setUrl("http://jxb-zbh.jxbscbd.com");
        newsList.add(newsItem);
        //组装图文消息相关信息
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUSerName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType("news");
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        //调用newsMessageToXml将图文消息转化为XML结构并返回
        return MessageUtil.newsMessageToXml(newsMessage);
    }
    /**
     * 图文消息转XML结构方法
     */
    public static String newsMessageToXml(NewsMessage message) {
        XStream xs = new XStream();
        //由于转换后xml根节点默认为class类，需转化为<xml>
        xs.alias("xml", message.getClass());
        xs.alias("item", new News().getClass());
        return xs.toXML(message);
    }



    /*s
     * 将文本消息对象转换成xml
     *
     *
     * */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map=new HashMap<String,String>();
        SAXReader reader=new SAXReader();//使用dom4j解析xml
        InputStream ins=request.getInputStream();//从request中获取输入流
        Document doc=reader.read(ins);
        Element root=doc.getRootElement();   //获取根元素
        List<Element> list=root.elements();   //获取所有的节点
        for(Element e:list){
            map.put(e.getName(), e.getText());
            System.out.println(e.getName()+"----->"+e.getText());
        }
        ins.close();   //关流
        return map;
    }
}
