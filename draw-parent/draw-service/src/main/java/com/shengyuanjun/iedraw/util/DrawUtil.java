package com.shengyuanjun.iedraw.util;

import com.shengyuanjun.iedraw.domain.Prize;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DrawUtil {
    /**
     * 根据Math.random()产生一个double型的随机数，判断每个奖品出现的概率
     * @param prizes
     * @return random：奖品列表prizes中的序列（prizes中的第random个就是抽中的奖品）
     */
    public static int getPrizeIndex(List<Prize> prizes) {
        DecimalFormat df = new DecimalFormat("######0.00");
        int random = -1;
        try{
            //计算总权重
            double sumWeight = 0;
            for(Prize p : prizes){
                sumWeight += p.getOdds();
            }

            //产生随机数
            double randomNumber;
            randomNumber = Math.random();

            //根据随机数在所有奖品分布的区域并确定所抽奖品
            double d1 = 0;
            double d2 = 0;
            for(int i=0;i<prizes.size();i++){
                d2 += Double.parseDouble(String.valueOf(prizes.get(i).getOdds()))/sumWeight;
                if(i==0){
                    d1 = 0;
                }else{
                    d1 +=Double.parseDouble(String.valueOf(prizes.get(i-1).getOdds()))/sumWeight;
                }
                if(randomNumber >= d1 && randomNumber <= d2){
                    random = i;
                    break;
                }
            }
        }catch(Exception e){
            System.out.println("生成抽奖随机数出错，出错原因：" +e.getMessage());
        }
        return random;
    }

    public static void main(String[] agrs) {
        int i = 0;
        DrawUtil a = new DrawUtil();
        int[] result=new int[4];
        List<Prize> prizes = new ArrayList<Prize>();

        Prize p1 = new Prize();
        p1.setPrizename("私人订制提货码");
        p1.setOdds((float) 0.001);//奖品的权重设置成0.001
        prizes.add(p1);

        Prize p2 = new Prize();
        p2.setPrizename("二维码");
        p2.setOdds((float) 1);//奖品的权重设置成1
        prizes.add(p2);

        Prize p3 = new Prize();
        p3.setPrizename("优惠券");
        p3.setOdds((float) 2);//奖品的权重设置成2
        prizes.add(p3);


        System.out.println("抽奖开始");
        for (i = 0; i < 10000; i++)// 打印100个测试概率的准确性
        {
            int selected=a.getPrizeIndex(prizes);
            System.out.println("第"+i+"次抽中的奖品为："+prizes.get(selected).getPrizename());
            result[selected]++;
            System.out.println("--------------------------------");
        }
        System.out.println("抽奖结束");
        System.out.println("每种奖品抽到的数量为：");
        System.out.println("一等奖："+result[0]);
        System.out.println("二等奖："+result[1]);
        System.out.println("三等奖："+result[2]);
    }
}