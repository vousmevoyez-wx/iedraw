package com.shengyuanjun.iedraw.web.controller;

import com.shengyuanjun.iedraw.AjaxResult;
import com.shengyuanjun.iedraw.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


@Api(value="登录controller",tags={"用户操作接口"})
@RestController
public class LoginController {

    @ApiOperation(value="登录请求",tags={"登录操作"},notes="要有用户名和密码")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody User user){
        if("admin".equals(user.getUsername())&&"admin".equals(user.getPassword())){
            return AjaxResult.me().setMessage("登录成功！").setRetObj(user);
        }
        return AjaxResult.me().setSuccess(false).setMessage("用户名或密码错误!");
    }

}