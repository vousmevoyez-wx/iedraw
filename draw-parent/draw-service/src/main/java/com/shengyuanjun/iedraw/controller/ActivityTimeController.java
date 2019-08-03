package com.shengyuanjun.iedraw.controller;

import com.shengyuanjun.iedraw.service.IActivityTimeService;
import com.shengyuanjun.iedraw.domain.ActivityTime;
import com.shengyuanjun.iedraw.query.ActivityTimeQuery;
import com.shengyuanjun.iedraw.AjaxResult;
import com.shengyuanjun.iedraw.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activityTime")
public class ActivityTimeController {
    @Autowired
    public IActivityTimeService activityTimeService;

    /**
    * 保存和修改公用的
    * @param activityTime  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody ActivityTime activityTime){
        try {
            if(activityTime.getId()!=null){
                activityTimeService.updateById(activityTime);
            }else{
                activityTimeService.insert(activityTime);
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
            activityTimeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ActivityTime get(@PathVariable("id") Long id)
    {
        return activityTimeService.selectById(id);
    }

    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<ActivityTime> list(){

        return activityTimeService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<ActivityTime> json(@RequestBody ActivityTimeQuery query)
    {
        Page<ActivityTime> page = new Page<ActivityTime>(query.getPage(),query.getRows());
            page = activityTimeService.selectPage(page);
            return new PageList<ActivityTime>(page.getTotal(),page.getRecords());
    }
}
