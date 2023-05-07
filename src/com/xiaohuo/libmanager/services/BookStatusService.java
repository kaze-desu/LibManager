package com.xiaohuo.libmanager.services;

import com.xiaohuo.libmanager.dao.BooksStatusDao;
import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.template.TypeList;

import java.util.ArrayList;

/**
 * Classify the book type and transfer the status to dao layer
 * @Author xiaohuo
 */
public class BookStatusService
{

    public void addBookStatus(String type, String identityCode, ArrayList<String>status) throws CollectionException
    {
        BooksStatusDao dao = new BooksStatusDao();
        if(TypeList.BOOK.getType().equals(type))
        {
            dao.addStatus();
        }
        else if(TypeList.JOURNAL.getType().equals(type))
        {
            dao.addStatus();
        }
        else if(TypeList.NEWSPAPER.getType().equals(type))
        {
            dao.addStatus();
        }
        else
        {
            throw new IllegalArgumentException("Invalid type, please check the type of the book including in template or not");
        }
    }
}

