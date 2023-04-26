package com.xiaohuo.libmanager.services;

import java.util.ArrayList;

/**
 * @author xiaohuo (Wang Boyun)
 */
public abstract class BaseBooks
{

    public String type;
    protected String tittle;
    protected String author;
    protected String publisher;
    protected String category;
    public BaseBooks(String tittle, String author, String publisher, String category)
    {
        this.tittle = tittle;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Add all the information of a book into an ArrayList.
     * @return an ArrayList of String(Not a BaseBooks!), which contains the information of a book.
     */
    public ArrayList<String> getBookInfo()
    {
        ArrayList<String> bookInfo = new ArrayList<>();
        bookInfo.add(type);
        bookInfo.add(tittle);
        bookInfo.add(author);
        bookInfo.add(publisher);
        bookInfo.add(category);
        return bookInfo;
    }
}
