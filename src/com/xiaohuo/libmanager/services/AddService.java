package com.xiaohuo.libmanager.services;


import com.xiaohuo.libmanager.dao.AddDao;
import com.xiaohuo.libmanager.exception.CollectionException;

import java.util.ArrayList;
/**
 * This service use to accept data from controller, then classify and unzip the information.
 * ArrayList always appearing in this class, I hope someone could optimize it if there has a smarter way.
 * @author xiaohuo(WANG BOYUN)
 */
public class AddService
{
    /**
     * The add function will transfer data to DAO, so it's not solving data, only transfer data.
     * @param booksInfo This param is a ArrayList that type of BaseBooks. For safety reason, I use a function (just like book.getXXX) to get unique value from each different extend class.
     *                 As you see, is not quite beautiful isn't it ?
     *                  4.29: Solved, so concision.
     */
    public void add(ArrayList<BaseBooks> booksInfo) throws CollectionException
    {
        AddDao add = new AddDao();
        add.initTable();
        String type = booksInfo.get(0).type;
        //To avoid magic value.
        String bookType = "Book";
        String journalType = "Journal";
        String newspaperType = "Newspaper";
        //Identify the type of the book, then transfer the ArrayList object to DAO.
        if (bookType.equals(type))
        {
            add.addBook(booksInfo);
        }
        else if (journalType.equals(type))
        {
            add.addJournal(booksInfo);
        }
        else if(newspaperType.equals(type))
        {
            add.addNewspaper(booksInfo);
        }
        else
        {
            throw new IllegalArgumentException("Invalid type, please check the type of the book including in template or not");
        }
    }
}
