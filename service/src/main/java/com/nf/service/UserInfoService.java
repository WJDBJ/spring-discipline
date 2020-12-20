package com.nf.service;

import com.nf.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author XJ
 */
public interface UserInfoService {
    void txdemo();

    List<UserInfoEntity> getAll(@Param("PageNum")int PageNum, @Param("PageSize")int PageSize);
}
