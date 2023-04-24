package com.xiaohuo.libmanager.Services;

import java.util.ArrayList;

/**
 * @author xiaohuo (Wang Boyun)
 */
public abstract class BooksBase
{
    String tittle;
    String author;
    String publisher;
    String category;

    public BooksBase(String tittle, String author, String publisher, String category)
    {
        this.tittle = tittle;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
    }
    public ArrayList<String> getBookInfo()
    {
        ArrayList<String> bookInfo = new ArrayList<>();
        bookInfo.add(tittle);
        bookInfo.add(author);
        bookInfo.add(publisher);
        bookInfo.add(category);
        return bookInfo;
    }
}
