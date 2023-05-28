package com.xiaohuo.libmanagergui.services;

import com.xiaohuo.libmanagergui.dao.BooksStatusDao;
import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.status.Status;
import com.xiaohuo.libmanagergui.services.template.TypeList;
import io.vproxy.vfx.ui.alert.StackTraceAlert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classify the book type and transfer the status to dao layer
 * @author xiaohuo(Wang Boyun)
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
                StackTraceAlert.showAndWait("书本类型不存在！",new CollectionException(exceptions));
            }
            dao.addStatus(bookId,location,status);
        }
        else
        {
            StackTraceAlert.showAndWait("书本类型不存在！",new CollectionException(exceptions));
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
            StackTraceAlert.showAndWait("书本类型不存在！",new CollectionException(exceptions));
        }
        return statusList;
    }

    @Override
    public Map<Integer, StatusData> searchAllBookStatus(String type, String identityCode) throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        BooksStatusDao dao = new BooksStatusDao();
        BooksManageServiceImpl manageService = new BooksManageServiceImpl();
        int bookId = manageService.getBookId(type,identityCode);
        Map<Integer,StatusData> statusList = new HashMap<>();
        if(checkBookType(type))
        {
            Map<Integer,ArrayList<Status>> statusRawList = dao.searchStatus(bookId);
            for(int statusId : statusRawList.keySet())
            {
                statusList.put(statusId,new StatusData(statusRawList.get(statusId).get(0).getLocation(),statusRawList.get(statusId).get(0).getStatus()));
            }
        }
        else
        {
            StackTraceAlert.showAndWait("书本类型不存在！",new CollectionException(exceptions));
        }
        return statusList;
    }

    @Override
    public ArrayList<Integer> getStatusId(Map<Integer, String> list)
    {
        ArrayList<Integer>statusIdList = new ArrayList<>();
        for (Map.Entry<Integer,String> entry:list.entrySet())
        {
            statusIdList.add(entry.getKey());
        }
        return statusIdList;
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
    public static class StatusData
    {
        public String location;
        public boolean status;
        public StatusData(String location,boolean status)
        {
            this.location = location;
            this.status = status;
        }

    }
}

