package com.shengyuanjun.iedraw.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.shengyuanjun.iedraw.AjaxResult;
import com.shengyuanjun.iedraw.PageList;
import com.shengyuanjun.iedraw.service.IWechatUserInfoService;
import com.shengyuanjun.iedraw.domain.WechatUserInfo;
import com.shengyuanjun.iedraw.query.WechatUserInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wechatUserInfo")
public class WechatUserInfoController {

    @Autowired
    public IWechatUserInfoService wechatUserInfoService;

    /**
    * 保存和修改公用的
    * @param wechatUserInfo  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody WechatUserInfo wechatUserInfo){
        try {
            if(wechatUserInfo.getId()!=null){
                wechatUserInfoService.updateById(wechatUserInfo);
            }else{
                wechatUserInfoService.insert(wechatUserInfo);
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
            wechatUserInfoService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public WechatUserInfo get(@PathVariable("id") Long id)
    {
        return wechatUserInfoService.selectById(id);
    }

    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<WechatUserInfo> list(){

        return wechatUserInfoService.selectList(null);
    }

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<WechatUserInfo> json(@RequestBody WechatUserInfoQuery query)
    {
        Page<WechatUserInfo> page = new Page<WechatUserInfo>(query.getPage(),query.getRows());
            page = wechatUserInfoService.selectPage(page);
            return new PageList<WechatUserInfo>(page.getTotal(),page.getRecords());
    }

}