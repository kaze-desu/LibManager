package com.xiaohuo.libmanager.services;


import com.xiaohuo.libmanager.dao.AddDao;
import com.xiaohuo.libmanager.services.template.Book;
import com.xiaohuo.libmanager.services.template.Journal;
import com.xiaohuo.libmanager.services.template.Newspaper;

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
     * @param bookInfo This param is a ArrayList that type of BaseBooks. For safety reason, I use a function (just like book.getXXX) to get unique value from each different extend class.
     *                 As you see, is not quite beautiful isn't it ?
     */
    public void add(ArrayList<BaseBooks> bookInfo)
    {
        AddDao add = new AddDao();
        String type = bookInfo.get(0).type;
        String bookType = "Book";
        String journalType = "Journal";
        String newspaperType = "Newspaper";
        if (bookType.equals(type))
        {
            for (BaseBooks baseBooks : bookInfo)
            {
                Book book = (Book) baseBooks;
                add.addBook(book.tittle, book.author, book.publisher, book.category, book.getIsbn());
            }
        }
        else if (journalType.equals(type))
        {
            for (BaseBooks baseBooks : bookInfo)
            {
                Journal journal = (Journal) baseBooks;
                add.addJournal(journal.tittle, journal.author, journal.publisher, journal.category,journal.getIssn());
            }
        }
        else if(newspaperType.equals(type))
        {
            for (BaseBooks baseBooks : bookInfo)
            {
                Newspaper newspaper = (Newspaper) baseBooks;
                add.addNewspaper(newspaper.tittle, newspaper.author, newspaper.publisher, newspaper.category, newspaper.getIssn(),newspaper.getCopyRight());
            }
        }
        else
        {
            throw new IllegalArgumentException("Invalid type, please check the type of the book including in template or not");
        }
    }
}
