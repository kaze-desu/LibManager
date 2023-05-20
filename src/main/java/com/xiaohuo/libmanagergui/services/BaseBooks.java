package com.xiaohuo.libmanagergui.services;

import java.util.ArrayList;

/**
 * BaseBooks is the base class of all types of book.
 * @author xiaohuo (Wang Boyun)
 */
public abstract class BaseBooks
{

    public String type;
    protected String tittle;
    protected String author;
    protected String publisher;
    protected String category;

    /**
     * The constructor of the BaseBooks.
     * @param tittle The tittle of the book.
     * @param author The author of the book.
     * @param publisher The publisher of the book.
     * @param category The category of the book.
     */
    public BaseBooks(String tittle, String author, String publisher, String category)
    {
        this.tittle = tittle;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
    }

    /**
     * Set the type(For example : "Newspaper") of the book.
     * @param type The type of the book.
     */
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

    public String getType()
    {
        return type;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getCategory()
    {
        return category;
    }

    public String getTittle()
    {
        return tittle;
    }
}
