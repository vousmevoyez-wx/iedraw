package com.shengyuanjun.iedraw.controller;

import com.shengyuanjun.iedraw.domain.ParticipationRestriction;
import com.shengyuanjun.iedraw.domain.PrizeRecord;
import com.shengyuanjun.iedraw.service.IParticipationRestrictionService;
import com.shengyuanjun.iedraw.service.IPrizeRecordService;
import com.shengyuanjun.iedraw.service.IPrizeService;
import com.shengyuanjun.iedraw.domain.Prize;
import com.shengyuanjun.iedraw.query.PrizeQuery;
import com.shengyuanjun.iedraw.AjaxResult;
import com.shengyuanjun.iedraw.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/prize")
public class PrizeController {

    @Autowired
    public IPrizeService prizeService;//奖品

    @Autowired
    public IParticipationRestrictionService participationRestrictionService;//中奖限制

    @Autowired
    public IPrizeRecordService prizeRecordService;//中奖纪录


    @RequestMapping(value = "/gogo")
    @ResponseBody
   public String getpost(){
       return "进来了";
   }


    /**
    * 保存和修改公用的
    * @param prize  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Prize prize){
        try {
            if(prize.getId()!=null){
                prizeService.updateById(prize);
            }else{
                prizeService.insert(prize);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            prizeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Prize get(@PathVariable("id") Long id)
    {
        return prizeService.selectById(id);
    }

    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Prize> list(){
        return prizeService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Prize> json(@RequestBody PrizeQuery query)
    {
        Page<Prize> page = new Page<Prize>(query.getPage(),query.getRows());
            page = prizeService.selectPage(page);
            return new PageList<Prize>(page.getTotal(),page.getRecords());
    }

    /**
     *    查询h5的商品
     */
    @RequestMapping(value = "/selectPrizeH5",method = RequestMethod.GET)
    public List<Prize> selectPrizeH5(){
        return prizeService.selectPrize();
    }

    /**
     *  抽奖
     *      id: 用户id
     *      rang1：当前用户的范围
     * @return
     *
     */
    @RequestMapping(value = "/drawPrize",method = RequestMethod.GET)
    public AjaxResult drawPrize(@PathVariable("id") Long id,String range1){
        //判断范围
        ParticipationRestriction participationRestriction = participationRestrictionService.selectById(1);
        String range = participationRestriction.getRange();//系统范围
        if (Long.parseLong(range) < Long.parseLong(range1)){
            return new AjaxResult("您未在当前抽奖范围。");
        }
        //判断抽奖次数
        String exceeddaily = participationRestriction.getExceeddaily();//系统日抽奖次数
        List<PrizeRecord> prizeRecords = prizeRecordService.selectRepeat(id);
        //开始抽奖

        //判断库存
        //中奖抽奖次数-1
        //返回
        return null;
    }

    /**
     * 分页条件查询优惠卷
     * @param query
     * @return
     */
    @RequestMapping(value = "/couponsbyquery",method = RequestMethod.GET)
    @ResponseBody
    public List<Prize> getCouponsByQuery(PrizeQuery query){

        return prizeService.getCouponsByQuery(query);
    }


    @RequestMapping(value = "/find")
    @ResponseBody
    public Object findPhoneNum(HttpServletRequest request,HttpServletResponse response,PrizeQuery query) throws Exception {

        response.setHeader("Access-Control-Allow-Origin","*");
        /*星号表示所有的域都可以接受，*/
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        /*S*//*tring phoneNum = request.getParameter("key");*/
        List<Prize> couponsByQuery = prizeService.getCouponsByQuery(query);
        return couponsByQuery;
    }




}