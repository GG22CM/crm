package com.fyj.crm.web.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("进入到验证有没有登陆过的过滤器");
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        //取得访问路径
        String path=request.getServletPath();

        //不应该被拦截的资源自动放行请求
        if("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            //直接放行
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            if(request.getSession().getAttribute("user")==null){
                //没有登陆，重定向到登陆页面,刷新浏览器地址栏
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }else{
                //登陆过，执行请求
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }


    }

    @Override
    public void destroy() {

    }
}
