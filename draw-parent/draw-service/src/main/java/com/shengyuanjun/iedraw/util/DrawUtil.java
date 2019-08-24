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
            for(int i=0; i<prizes.size(); i++){
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
        int[] result=new int[7];
        List<Prize> prizes = new ArrayList<Prize>();

        Prize p1 = new Prize();
        p1.setPrizename("p1");
        p1.setOdds((float) 0.3);//奖品的权重设置成0.001
        prizes.add(p1);

        Prize p2 = new Prize();
        p2.setPrizename("p2");
        p2.setOdds((float) 0.2);//奖品的权重设置成1
        prizes.add(p2);

        Prize p3 = new Prize();
        p3.setPrizename("p3");
        p3.setOdds((float) 0.2);//奖品的权重设置成2
        prizes.add(p3);

        Prize p4 = new Prize();
        p4.setPrizename("p4");
        p4.setOdds((float) 0.05);//奖品的权重设置成2
        prizes.add(p4);

        Prize p5 = new Prize();
        p5.setPrizename("p5");
        p5.setOdds((float) 0.4);//奖品的权重设置成2
        prizes.add(p5);

        Prize p6 = new Prize();
        p6.setPrizename("p6");
        p6.setOdds((float) 0.23);//奖品的权重设置成2
        prizes.add(p6);

        Prize p7 = new Prize();
        p7.setPrizename("p7");
        p7.setOdds((float) 0.11);//奖品的权重设置成2
        prizes.add(p7);

        System.out.println("抽奖开始");
        int[] result2=new int[7];
        int preSelected = 0;
        for (i = 0; i < 100; i++)// 打印100个测试概率的准确性
        {
            int selected=a.getPrizeIndex(prizes);
            System.out.println("第"+i+"次抽中的奖品为："+prizes.get(selected).getPrizename());
            result[selected]++;
            if ( preSelected == selected ){
                result2[selected]++;
            }
            preSelected = selected;
            System.out.println("--------------------------------");
        }
        System.out.println("抽奖结束");
        System.out.println("每种奖品抽到的数量为：");
        System.out.println("p1："+result[0]);
        System.out.println("p2："+result[1]);
        System.out.println("p3："+result[2]);
        System.out.println("p4："+result[3]);
        System.out.println("p5："+result[4]);
        System.out.println("p6："+result[5]);
        System.out.println("p7："+result[6]);
        System.out.println("p1："+result2[0]);
        System.out.println("p2："+result2[1]);
        System.out.println("p3："+result2[2]);
        System.out.println("p4："+result2[3]);
        System.out.println("p5："+result2[4]);
        System.out.println("p6："+result2[5]);
        System.out.println("p7："+result2[6]);
    }
}