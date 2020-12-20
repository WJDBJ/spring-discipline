package com.nf.controller;

import com.github.pagehelper.PageInfo;
import com.nf.entity.UserInfoEntity;
import com.nf.service.UserInfoService;
import com.nf.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author XJ
 */
@Controller
public class LoginController {
    @Autowired
    UserInfoService userInfoService;

    @RequestMapping("/list")
    public ResponseVO getAll(@RequestParam(value = "pageNum", required = false,
            defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",
            required = false,defaultValue = "5") int pageSize){
        List<UserInfoEntity> userInfoEntityList = userInfoService.getAll(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(userInfoEntityList,3);
        ResponseVO responseVo = new ResponseVO("200","ok",pageInfo);
        return responseVo;
    }
}
