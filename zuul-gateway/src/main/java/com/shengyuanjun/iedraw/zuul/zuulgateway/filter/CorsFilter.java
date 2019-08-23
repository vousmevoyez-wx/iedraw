//package com.shengyuanjun.iedraw.zuul.zuulgateway.filter;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class CorsFilter implements Filter {
//
//    @Override
//    public void destroy() {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response,
//                         FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse rep = (HttpServletResponse) response;
//        // 设置允许多个域名请求
//        String originHeads = req.getHeader("Origin");
//        rep.setHeader("Access-Control-Allow-Origin", originHeads);
//        // 设置服务器允许浏览器发送请求都携带cookie
//        rep.setHeader("Access-Control-Allow-Credentials", "true");
//        // 允许的访问方法
//        rep.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
//        // Access-Control-Max-Age 用于 CORS 相关配置的缓存 你现在你本地测试再给别人啊
//        rep.setHeader("Access-Control-Max-Age", "3600");
//        rep.setHeader("Access-Control-Allow-Headers", "token,Origin, X-Requested-With, Content-Type, Accept,mid,X-Token");
//        response.setCharacterEncoding("UTF-8");
//
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void init(FilterConfig arg0) throws ServletException {
//
//    }
//}
