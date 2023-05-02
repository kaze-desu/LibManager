package com.xiaohuo.libmanager.services.template;

import com.xiaohuo.libmanager.services.BaseBooks;

import java.util.ArrayList;

/**
 * Book is a type of Books.
 * @Type: Book
 * @see BaseBooks
 * @author Xiaohuo (Wang Boyun)
 */
public class Book extends BaseBooks
{
    private final String isbn;

    /**
     * This is the constructor of Book, which is used to initialize the information of a book.
     */
    public Book(String tittle, String author, String publisher, String category,String isbn)
    {
        super(tittle, author, publisher, category);
        this.isbn = isbn;

        String type = TypeList.BOOK.getType();
        setType(type);
    }

    /**
     * This method is used to get the information of a book.
     * @return an ArrayList of String(Not a BaseBooks!), which contains the information of a book.
     */
    @Override
    public ArrayList<String> getBookInfo()
    {
        ArrayList<String> bookInfo = super.getBookInfo();
        bookInfo.add(isbn);
        return bookInfo;
    }

    /**
     * This method is used to get the ISBN of a book.
     * @return the ISBN of a book.
     */
    public String getIsbn()
    {
        return isbn;
    }
}
