package com.nf.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nf.vo.ResponseVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cj
 * @date 2020/1/2
 */
public class RestAuthFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseVO vo = new ResponseVO();
        vo.setCode("500");
        vo.setMsg("login fail");

        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(response.getOutputStream(), vo);
    }
}
