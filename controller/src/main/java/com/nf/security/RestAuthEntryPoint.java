package com.nf.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nf.vo.ResponseVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cj
 * @date 2020/1/2
 */
public class RestAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseVO responseVO = new ResponseVO();
        //403表示未授权
        responseVO.setCode("403");
        responseVO.setMsg("login first!");
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getOutputStream(), responseVO);
    }
}
