package com.xiaohuo.libmanager.templete;

import com.xiaohuo.libmanager.Services.BooksBase;

import java.util.ArrayList;

/**
 * Book is a class that extends BooksBase
 * It is used to create a book object
 * It has an attribute: isbn
 * It has a constructor and a method to get the information of a book
 * @see com.xiaohuo.libmanager.Services.BooksBase
 * @author xiaohuo(WANG BOYUN)
 */
public class Book extends BooksBase
{
    private String isbn;
    public Book(String tittle, String author, String publisher, String category,String isbn)
    {
        super(tittle, author, publisher, category);
        this.isbn = isbn;
    }

    /**
     * This method is used to get the information of a book.
     * @return an ArrayList of String, which contains the information of a book.
     */
    @Override
    public ArrayList<String> getBookInfo()
    {
        ArrayList<String> bookInfo = super.getBookInfo();
        bookInfo.add(isbn);
        return bookInfo;
    }
}
