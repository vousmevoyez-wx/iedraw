package com.shengyuanjun.iedraw.test;


import com.shengyuanjun.iedraw.domain.PrizeRecord;
import com.shengyuanjun.iedraw.service.IPrizeRecordService;
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
@Component
public class OutOfTimeShow {

    @Resource
    private IPrizeRecordService prizeRecordServiceImpl;

    //系统启动执行一次
    @Scheduled(initialDelay=3000, fixedRate = 60*60*1000*6)//每6小时执行一次
    public void showOutOfTIme(){
        System.out.println("查询过期记录");
        //查看所有记录
        List<PrizeRecord> list = prizeRecordServiceImpl.selectAll();
        Iterator<PrizeRecord> it = list.iterator();
        while(it.hasNext()){
            PrizeRecord pz = it.next();
            System.out.println(pz);
            //当天有效
            if(!Timeutil.getDayTime(new Date().getTime()+"").equals(Timeutil.getDayTime(pz.getCreatetime()+""))){
                pz.setStatus(2);
                System.out.println(prizeRecordServiceImpl.updateStatus(pz));
            }
        }
    }
}