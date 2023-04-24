package com.xiaohuo.libmanager.templete;

import com.xiaohuo.libmanager.Services.BooksBase;

import java.util.ArrayList;

/**
 * Journal is a class that extends BooksBase
 * It is used to create a journal object
 * It has an attribute: issn
 * It has a constructor and a method to get the information of a journal
 * @see com.xiaohuo.libmanager.Services.BooksBase
 * @author xiaohuo(WANG BOYUN)
 */
public class Journal extends BooksBase
{
    private String issn;
    public Journal(String tittle, String author, String publisher, String category,String issn)
    {
        super(tittle, author, publisher, category);
        this.issn = issn;
    }

    /**
     * This method is used to get the information of a journal.
     * @return an ArrayList of String, which contains the information of a journal.
     */
    @Override
    public ArrayList<String> getBookInfo()
    {
        ArrayList<String> journal = super.getBookInfo();
        journal.add(issn);
        return journal;
    }
}
