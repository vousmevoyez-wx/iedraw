package com.shengyuanjun.iedraw.controller;

import com.shengyuanjun.iedraw.service.IPrizeRecordService;
import com.shengyuanjun.iedraw.domain.PrizeRecord;
import com.shengyuanjun.iedraw.query.PrizeRecordQuery;
import com.shengyuanjun.iedraw.AjaxResult;
import com.shengyuanjun.iedraw.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prizeRecord")
public class PrizeRecordController {
    @Autowired
    public IPrizeRecordService prizeRecordService;

    /**
    * 保存和修改公用的
    * @param prizeRecord  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody PrizeRecord prizeRecord){
        try {
            if(prizeRecord.getId()!=null){
                prizeRecordService.updateById(prizeRecord);
            }else{
                prizeRecordService.insert(prizeRecord);
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
            prizeRecordService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public PrizeRecord get(@PathVariable("id") Long id)
    {
        return prizeRecordService.selectById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<PrizeRecord> list(){

        return prizeRecordService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<PrizeRecord> json(@RequestBody PrizeRecordQuery query)
    {
        Page<PrizeRecord> page = new Page<PrizeRecord>(query.getPage(),query.getRows());
            page = prizeRecordService.selectPage(page);
            return new PageList<PrizeRecord>(page.getTotal(),page.getRecords());
    }
}
