package com.shengyuanjun.iedraw.test;

/**
 * @program: gzher
 * @description:
 * @author: gq544
 * @create: 2019-08-05 11:23
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * https://my.oschina.net/ydsakyclguozi/blog/887368
 *
 */
public class GenerateCode {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    /*public static void main(String[] args) {
        try {
            boolean flag = generateCode("518");
            if (flag) {
                System.out.println("成功生成二维码");
            }
        } catch (WriterException | IOException e) {
            System.err.println("生成二维码失败");
            e.printStackTrace();
        }
    }*/

    public static boolean generateCode(String from,String msg,String name) {
        // 这里是URL，扫描之后就跳转到这个界面
        String text = msg;
        String path = "E:/"; // 图片生成的位置
        int width = 400;
        int height = 400;
        // 二维码图片格式
        String format = "jpg";
        // 设置编码，防止中文乱码
        Hashtable<EncodeHintType, Object> ht = new Hashtable<EncodeHintType, Object>();
        ht.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 设置二维码参数(编码内容，编码类型，图片宽度，图片高度,格式)
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, ht);
    } catch (WriterException e) {
        e.printStackTrace();
    }
        /*// 生成二维码(定义二维码输出服务器路径)
        File outputFile = new File(path);
        if (!outputFile.exists()) {
            // 创建文件夹
            outputFile.mkdir();
        }
        */
        int b_width = bitMatrix.getWidth();
        int b_height = bitMatrix.getHeight();
        // 建立图像缓冲器
        BufferedImage image = new BufferedImage(b_width, b_height, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < b_width; x++) {
            for (int y = 0; y < b_height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }
        // 生成二维码
        try {
            File tempFile = new File("/user/jxb/" + from);
            if (!tempFile.exists()) {

                tempFile.mkdirs();
            }
            ImageIO.write(image, format, new File("/"+from+"/"+name+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 二维码的名称
        // code.jpg

        return true;
    }
}
