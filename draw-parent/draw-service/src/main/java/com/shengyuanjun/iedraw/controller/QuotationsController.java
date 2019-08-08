package com.shengyuanjun.iedraw.controller;

import com.shengyuanjun.iedraw.service.IQuotationsService;
import com.shengyuanjun.iedraw.domain.Quotations;
import com.shengyuanjun.iedraw.query.QuotationsQuery;
import com.shengyuanjun.iedraw.AjaxResult;
import com.shengyuanjun.iedraw.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotations")
public class QuotationsController {
    @Autowired
    public IQuotationsService quotationsService;

    /**
    * 保存和修改公用的
    * @param quotations  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Quotations quotations){
        try {
            if(quotations.getId()!=null){
                quotationsService.updateById(quotations);
            }else{
                quotationsService.insert(quotations);
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
            quotationsService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Quotations get(@PathVariable("id") Long id)
    {
        return quotationsService.selectById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Quotations> list(){

        return quotationsService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Quotations> json(@RequestBody QuotationsQuery query)
    {
        Page<Quotations> page = new Page<Quotations>(query.getPage(),query.getRows());
            page = quotationsService.selectPage(page);
            return new PageList<Quotations>(page.getTotal(),page.getRecords());
    }

    /**
     *  返回语录
     */
    @RequestMapping(value = "/returnQuotations",method = RequestMethod.POST)
    public AjaxResult returnQuotations(Long quotationsstatus,String quotationsdesc){

        return null;
    }

}