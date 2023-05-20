package com.xiaohuo.libmanagergui.services;

import com.xiaohuo.libmanagergui.dao.UserManagerDao;

/**
 * @author XU XIAO
 */

//TODO Please use Interface to regulate your code.
public class UserManagerServiceImpl
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