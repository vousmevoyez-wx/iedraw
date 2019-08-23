package com.shengyuanjun.iedraw.controller;

import com.shengyuanjun.iedraw.encrypt.MD5;
import com.shengyuanjun.iedraw.service.ITokenuserService;
import com.shengyuanjun.iedraw.domain.Tokenuser;
import com.shengyuanjun.iedraw.query.TokenuserQuery;
import com.shengyuanjun.iedraw.AjaxResult;
import com.shengyuanjun.iedraw.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tokenuser")
public class TokenuserController {

    @Value("${gzh.appid}")
    private String appid;

    @Value("${gzh.appsecret}")
    private String appsecret;

    @Autowired
    public ITokenuserService tokenuserService;

    public static void main(String[] args) {
        String time = new Date().getTime()+"";
        String str = "wxaf662218893cdffa" + "9f0ab14aa8d9491a9e30453813a1ee6d" + time;
        String sign = MD5.getMD5(str);
        System.out.println("appid = " + "wxaf662218893cdffa");
        System.out.println("appsecret = " + "9f0ab14aa8d9491a9e30453813a1ee6d");;
        System.out.println("timestamp = " + time);
        System.out.println("sign = " + sign);
    }



    @RequestMapping("/token")
    public Map  getToken(HttpServletRequest request) {
        Map map = new HashMap();
        String AppId = request.getParameter("appId");
        String timestamp = request.getParameter("timestamp");
        String sign = request.getParameter("sign");
        Tokenuser token = new Tokenuser();
        token.setAppId(AppId);
        // token.setAppScr(AppSrc);
        List<Tokenuser> tokenusers = tokenuserService.selectByAppId(token);
        Tokenuser tokenuser = new Tokenuser();
        System.out.println("tokenusers = " + tokenusers);
        for (int i = 0; i < tokenusers.size(); i++) {
            tokenuser = tokenusers.get(i);

            System.out.println("msg = " + (AppId + tokenuser.getAppScr() + timestamp));
            String value  = MD5.getMD5(AppId + tokenuser.getAppScr() + timestamp);
            System.out.println("value = " + value);

            System.out.println("value = " + value);
            System.out.println("sign = "+sign);
            if(!value.equals(sign)){
                map.put("msg","token验证未通过");
                map.put("code","201");
                map.put("success","false");
                return map;
            }

            Map mp = new HashMap();
            System.out.println("tokenuser = " + tokenuser);
            if(tokenuser.getToken()==null){
                System.out.println("进来取信token");
                String md5t = MD5.getMD5( AppId + tokenuser.getAppScr()+new Date().getTime());
                tokenuser.setToken(md5t);
                tokenuser.setTokenDate(new Date());
                tokenuserService.updateById(tokenuser);

                map.put("msg","兑换成功");
                map.put("code","200");
                map.put("data","{\"token\":\"" + tokenuser.getToken() + "\"}");
                map.put("success","true");
                return map;
            }else{
                Long time  = new Date().getTime();
                Long oldtime = tokenuser.getTokenDate().getTime();
                //修改token有效期
                //如果时间超过了有效时间(1小时)重新生成一个并返回
                if((time-oldtime) > (60*60*1000) ){
                    String md51 = MD5.getMD5( AppId + tokenuser.getAppScr()+new Date().getTime());
                    tokenuser.setToken(md51);
                    tokenuser.setTokenDate(new Date());
                    tokenuserService.updateById(tokenuser);

                    map.put("msg","兑换成功");
                    map.put("code","200");
                    map.put("data","{\"token\":\"" + tokenuser.getToken() + "\"}");
                    map.put("success","true");
                    return map;
                }else{
                    //如果未超时，直接返回token值
                    map.put("msg","兑换成功");
                    map.put("code","200");
                    map.put("data","{\"token\":\"" + tokenuser.getToken() + "\"}");
                    map.put("success","true");
                    return map;
                }
            }
        }
        map.put("msg","没有兑换token权限的appid");
        map.put("code","201");
        map.put("success","false");
        return map;
    }

    /**
     * 保存和修改公用的
     * @param tokenuser  传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value="/",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Tokenuser tokenuser){
        try {
            if(tokenuser.getId()!=null){
                tokenuserService.updateById(tokenuser);
            }else{
                tokenuserService.insert(tokenuser);
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
            tokenuserService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Tokenuser get(@PathVariable("id") Long id)
    {
        return tokenuserService.selectById(id);
    }

    /**
     * 查看所有信息
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Tokenuser> list(){

        return tokenuserService.selectList(null);
    }


    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Tokenuser> json(@RequestBody TokenuserQuery query)
    {
        Page<Tokenuser> page = new Page<Tokenuser>(query.getPage(),query.getRows());
        page = tokenuserService.selectPage(page);
        return new PageList<Tokenuser>(page.getTotal(),page.getRecords());
    }
}
