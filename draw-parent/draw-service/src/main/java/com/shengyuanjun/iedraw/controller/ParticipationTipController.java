package com.shengyuanjun.iedraw.controller;

import com.shengyuanjun.iedraw.service.IParticipationTipService;
import com.shengyuanjun.iedraw.domain.ParticipationTip;
import com.shengyuanjun.iedraw.query.ParticipationTipQuery;
import com.shengyuanjun.iedraw.AjaxResult;
import com.shengyuanjun.iedraw.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participationTip")
public class ParticipationTipController {
    @Autowired
    public IParticipationTipService participationTipService;

    /**
    * 保存和修改公用的
    * @param participationTip  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody ParticipationTip participationTip){
        try {
            if(participationTip.getId()!=null){
                participationTipService.updateById(participationTip);
            }else{
                participationTipService.insert(participationTip);
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
            participationTipService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ParticipationTip get(@PathVariable("id") Long id)
    {
        return participationTipService.selectById(id);
    }

    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<ParticipationTip> list(){

        return participationTipService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<ParticipationTip> json(@RequestBody ParticipationTipQuery query)
    {
        Page<ParticipationTip> page = new Page<ParticipationTip>(query.getPage(),query.getRows());
            page = participationTipService.selectPage(page);
            return new PageList<ParticipationTip>(page.getTotal(),page.getRecords());
    }

}
