package com.nf.service.impl;

import com.nf.dao.UserInfoDao;
import com.nf.entity.UserInfoEntity;
import com.nf.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XJ
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired(required = false)
    private UserInfoDao userInfoDao;

    @Transactional
    @Override
    public void txdemo() {
        userInfoDao.delete();
        userInfoDao.insert();
    }

    @Override
    public List<UserInfoEntity> getAll(int PageNum, int PageSize) {
        return userInfoDao.getAll(PageNum,PageSize);
    }
}
