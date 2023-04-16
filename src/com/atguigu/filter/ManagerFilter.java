package com.atguigu.filter;

import com.atguigu.pojo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ManagerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            servletRequest.setAttribute("msg","请先登录再进行操作");
            request.getRequestDispatcher("/pages/user/login.jsp").forward(servletRequest,servletResponse);

        }else if (!"admin".equals(user.getUsername())){
            servletRequest.setAttribute("msg","只有管理员才能进行操作");
            request.getRequestDispatcher("/pages/user/login.jsp").forward(servletRequest,servletResponse);
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
