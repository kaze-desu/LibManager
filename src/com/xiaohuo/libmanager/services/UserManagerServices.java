package com.xiaohuo.libmanager.services;

import com.xiaohuo.libmanager.dao.UserManagerDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XU XIAO
 */
public class UserManagerServices
{

    public void addUser(String name, String password)
    {
        if(!verifyNameExist(name))
        {
            //TODO 完成ADD
        }
        else
        {
            throw new RuntimeException("用户名已存在");
        }

    }
    public boolean verifyNameExist(String name)
    {
        UserManagerDao userManagerDao = new UserManagerDao();
        userManagerDao.verifyNameExist(name);
        return false;
    }



}
//将管理者的信息转移到数据库里头