package com.shengyuanjun.iedraw.controller;

import com.shengyuanjun.iedraw.service.ICustomizeService;
import com.shengyuanjun.iedraw.domain.Customize;
import com.shengyuanjun.iedraw.query.CustomizeQuery;
import com.shengyuanjun.iedraw.AjaxResult;
import com.shengyuanjun.iedraw.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customize")
public class CustomizeController {
    @Autowired
    public ICustomizeService customizeService;

    /**
    * 保存和修改公用的
    * @param customize  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Customize customize){
        try {
            if(customize.getId()!=null){
                customizeService.updateById(customize);
            }else{
                customizeService.insert(customize);
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
            customizeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Customize get(@PathVariable("id") Long id)
    {
        return customizeService.selectById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Customize> list(){

        return customizeService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Customize> json(@RequestBody CustomizeQuery query)
    {
//        Page<Customize> page = new Page<Customize>(query.getPage(),query.getRows());
//            page = customizeService.selectPage(page);
//            return new PageList<Customize>(page.getTotal(),page.getRecords());

        return customizeService.selectCustomizePage(query);
    }

}