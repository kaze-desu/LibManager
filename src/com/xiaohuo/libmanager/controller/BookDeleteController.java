package com.xiaohuo.libmanager.controller;

import com.xiaohuo.libmanager.dao.BooksManageDao;
import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BooksManageService;


import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.Map;

public class BookDeleteController {
    public void deleteBook() throws CollectionException
    {
        BooksManageService service = new BooksManageService();
        Map<Integer, ArrayList<String>> result;
        String title = ""; // user enter book title
        result = service.search(title);
        if(result.size()>1){
            // show all the results and ask user to choose one

            int key = 0; // the key code in map for the book the user want to delete
            String identityType = ""; // get book type
            String identityCode = ""; // get book code

            int ID = service.getBookID(identityType,identityCode);
            String sql = "BookID="+ID;
            service.deleteBookByID(sql);
        }
        else if(result.size()==1){
            String sql = "Title="+title;
            service.deleteBookByTitle(sql);
        }
        else{
            // its empty
        }
    }
}
