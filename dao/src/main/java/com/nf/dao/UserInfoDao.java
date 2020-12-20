package com.nf.dao;

import com.nf.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author XJ
 */
public interface UserInfoDao {
    int insert();

    int delete();

    List<UserInfoEntity> getAll(@Param("PageNum")int PageNum,@Param("PageSize")int PageSize);
}
