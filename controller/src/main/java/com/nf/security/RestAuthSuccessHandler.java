package com.nf.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nf.vo.ResponseVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cj
 * @date 2020/1/2
 */
public class RestAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResponseVO vo = new ResponseVO();
        vo.setCode("200");
        vo.setMsg("login ok");
        vo.setData(authentication.getName());
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(response.getOutputStream(), vo);
    }
}
