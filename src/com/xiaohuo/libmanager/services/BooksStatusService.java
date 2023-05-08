package com.xiaohuo.libmanager.services;

import com.xiaohuo.libmanager.dao.BooksStatusDao;
import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.template.TypeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Classify the book type and transfer the status to dao layer
 * @author xiaohuo
 */
public class BooksStatusService
{

    public void addBookStatus(String type, String identityCode, String location,boolean status) throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        BooksStatusDao dao = new BooksStatusDao();
        BooksManageService manageService = new BooksManageService();
        if(!checkBookType(type))
        {
            exceptions.add(new Throwable("The book type is not exist!"));
            throw new CollectionException(exceptions);
        }
        int bookId = manageService.getBookID(type,identityCode);
        if(bookId == -1)
        {
            exceptions.add(new Throwable("The book is not exist!"));
            throw new CollectionException(exceptions);
        }
        dao.addStatus(bookId,location,status);
    }
    public boolean checkBookType(String type) throws CollectionException
    {
        for (TypeList list:TypeList.values())
        {
            if (list.getType().equals(type))
            {
                return true;
            }
        }
        return false;
    }
}

