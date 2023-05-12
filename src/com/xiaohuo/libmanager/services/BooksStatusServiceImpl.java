package com.xiaohuo.libmanager.services;

import com.xiaohuo.libmanager.dao.BooksStatusDao;
import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.status.Status;
import com.xiaohuo.libmanager.services.template.TypeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classify the book type and transfer the status to dao layer
 * @author xiaohuo
 */
public class BooksStatusServiceImpl implements BooksStatusService
{

    @Override
    public void addBookStatus(String type, String identityCode, String location,boolean status) throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        BooksStatusDao dao = new BooksStatusDao();
        BooksManageServiceImpl manageService = new BooksManageServiceImpl();
        if(checkBookType(type))
        {
            int bookId = manageService.getBookId(type,identityCode);
            if(bookId == -1)
            {
                exceptions.add(new Throwable("The book is not exist!"));
                throw new CollectionException(exceptions);
            }
            dao.addStatus(bookId,location,status);
        }
        else
        {
            exceptions.add(new Throwable("The book type is not exist!"));
            throw new CollectionException(exceptions);
        }
    }
    @Override
    public boolean checkBookType(String type)
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
    @Override
    public Map<Integer,String> searchBookStatus(String type, String identityCode) throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        BooksStatusDao dao = new BooksStatusDao();
        BooksManageServiceImpl manageService = new BooksManageServiceImpl();
        int bookId = manageService.getBookId(type,identityCode);
        Map<Integer,String> statusList = new HashMap<>();
        if(checkBookType(type))
        {
            Map<Integer,ArrayList<Status>> statusRawList = dao.searchStatus(bookId);
            for(int statusId : statusRawList.keySet())
            {
                if (statusRawList.get(statusId).get(0).getStatus())
                {
                    statusList.put(statusId,statusRawList.get(statusId).get(0).getLocation());
                }

            }
        }
        else
        {
            exceptions.add(new Throwable("The book type is not exist!"));
            throw new CollectionException(exceptions);
        }
        return statusList;
    }

    @Override
    public void editBookLocation(int statusId, String location) throws CollectionException
    {
        BooksStatusDao dao = new BooksStatusDao();
        String column = "Location";
        dao.editStatus(statusId,column,location);
    }
    @Override
    public void borrowBook(int statusId) throws CollectionException
    {
        BooksStatusDao dao = new BooksStatusDao();
        String column = "Status";
        dao.editStatus(statusId,column,false);
    }
    @Override
    public void revertBook(int statusId) throws CollectionException
    {
        BooksStatusDao dao = new BooksStatusDao();
        String column = "Status";
        dao.editStatus(statusId,column,true);
    }
    @Override
    public void deleteBookStatus(int statusId) throws CollectionException
    {
        BooksStatusDao dao = new BooksStatusDao();
        dao.deleteStatus(statusId);
    }
}

