package com.shengyuanjun.iedraw.controller;

import com.shengyuanjun.iedraw.service.IParticipationRestrictionService;
import com.shengyuanjun.iedraw.domain.ParticipationRestriction;
import com.shengyuanjun.iedraw.query.ParticipationRestrictionQuery;
import com.shengyuanjun.iedraw.AjaxResult;
import com.shengyuanjun.iedraw.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participationRestriction")
public class ParticipationRestrictionController {
    @Autowired
    public IParticipationRestrictionService participationRestrictionService;

    /**
    * 保存和修改公用的
    * @param participationRestriction  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody ParticipationRestriction participationRestriction){
        try {
            if(participationRestriction.getId()!=null){
                participationRestrictionService.updateById(participationRestriction);
            }else{
                participationRestrictionService.insert(participationRestriction);
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
            participationRestrictionService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ParticipationRestriction get(@PathVariable("id") Long id)
    {
        return participationRestrictionService.selectById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<ParticipationRestriction> list(){

        return participationRestrictionService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<ParticipationRestriction> json(@RequestBody ParticipationRestrictionQuery query)
    {
        Page<ParticipationRestriction> page = new Page<ParticipationRestriction>(query.getPage(),query.getRows());
            page = participationRestrictionService.selectPage(page);
            return new PageList<ParticipationRestriction>(page.getTotal(),page.getRecords());
    }

    /**
     *  地理围栏判断距离
     * @return
     */
    public AjaxResult geographical(){
        return null;
    }

}