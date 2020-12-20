package com.nf.controller;

import com.github.pagehelper.PageInfo;
import com.nf.entity.UserInfoEntity;
import com.nf.service.UserInfoService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author XJ
 */
@WebServlet("/list")
public class UserInfoListServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context
                = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
        UserInfoService userInfoService = context.getBean(UserInfoService.class);
        int pageNum = ServletRequestUtils.getIntParameter(req,"PageNum",1);
        int pageSize = ServletRequestUtils.getIntParameter(req,"PageSize",3);
        List<UserInfoEntity> result = userInfoService.getAll(pageNum,pageSize);
        PageInfo pageInfo = new PageInfo(result,3);
        req.setAttribute("pageInfo",pageInfo);
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
