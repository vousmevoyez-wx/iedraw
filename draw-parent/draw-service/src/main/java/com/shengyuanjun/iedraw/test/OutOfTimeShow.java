package com.shengyuanjun.iedraw.test;


import com.shengyuanjun.iedraw.domain.Prize;
import com.shengyuanjun.iedraw.domain.PrizeRecord;
import com.shengyuanjun.iedraw.service.IPrizeRecordService;
import com.shengyuanjun.iedraw.service.IPrizeService;
import com.shengyuanjun.iedraw.util.timeUtil.Timeutil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @program: 查看中奖信息是否过期(当天有效)
 * @description:
 * @author: gq544
 * @create: 2019-08-11 23:17
 */

/*@Component
public class OutOfTimeShow {

    @Resource
    private IPrizeRecordService prizeRecordServiceImpl;

    @Resource
    private IPrizeService prizeServiceImpl;

    //系统启动执行一次
    @Scheduled(initialDelay=3000, fixedRate = 60*60*1000*6)//每小时执行一次
    public void showOutOfTIme(){
        System.out.println("查询过期记录保证中奖记录有效性控制...");
        //查看所有记录
        List<PrizeRecord> list = prizeRecordServiceImpl.selectAll();
        System.out.println("list = "+list);
        Iterator<PrizeRecord> it = list.iterator();
        while(it.hasNext()){
            PrizeRecord pz = it.next();
            Prize pr = prizeServiceImpl.selectPrizeById(pz.getPrizeid());
            //判断如果现在时间超出了礼品最后期限，则算失效
            if((new Date().getTime()-Long.parseLong(pr.getEndvalidityperiod()+"000")) > 0){
                //超过领奖期限的领奖信息如果不是已领取状态，则自动修改为过期状态
                if("0".equals(pz.getStatus()+"")){
                    pz.setStatus(2);
                    System.out.println(prizeRecordServiceImpl.updateStatus(pz));
                }
            }
        }
    }
}
*/
