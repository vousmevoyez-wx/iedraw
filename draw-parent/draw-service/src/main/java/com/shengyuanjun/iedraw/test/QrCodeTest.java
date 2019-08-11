package com.shengyuanjun.iedraw.test;


import com.shengyuanjun.iedraw.util.erweimautil.QRCodeUtil;
import org.springframework.stereotype.Component;


/**
 * @program: ewm
 * @description:
 * @author: gq544
 * @create: 2019-08-03 18:41
 */

@Component
public class QrCodeTest {

    public void show(String from,String msg,String name) {

        // 存放在二维码中的内容
        String text = msg;

        // 嵌入二维码的图片路径
        String imgPath = "D://prize.png";
        // 生成的二维码的路径及名称
        String destPath = "/"+from+"/"+name+"mycat.png";
        //生成二维码
        try {
            QRCodeUtil.encode(text, imgPath, destPath, true);
        // 解析二维码
        String str = QRCodeUtil.decode(destPath);
        // 打印出解析出的内容
        System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}