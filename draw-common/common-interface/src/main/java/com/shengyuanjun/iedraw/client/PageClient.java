package com.shengyuanjun.iedraw.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "DRAW-COMMON")
public interface PageClient {
    /**
     * 调用这个接口可以
     *  根据接口的数据，按照指定的模板，将页面生成到指定的位置
     * @param model 数据
     * @param templatePath 模板的路径
     * @param staticPagePah 生成的静态资源的路径
     */
    @RequestMapping(value = "/staticPage",method = RequestMethod.POST)
    public void genStaticPage(@RequestBody Object model,
                              @RequestParam("templatePath") String templatePath,
                              @RequestParam("staticPagePah") String staticPagePah);

}