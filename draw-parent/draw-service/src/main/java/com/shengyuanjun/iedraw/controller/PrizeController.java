package com.shengyuanjun.iedraw.controller;

import com.shengyuanjun.iedraw.service.IPrizeService;
import com.shengyuanjun.iedraw.domain.Prize;
import com.shengyuanjun.iedraw.query.PrizeQuery;
import com.shengyuanjun.iedraw.AjaxResult;
import com.shengyuanjun.iedraw.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prize")
public class PrizeController {
    @Autowired
    public IPrizeService prizeService;

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
}
