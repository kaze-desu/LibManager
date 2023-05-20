package com.xiaohuo.libmanagergui.services;

/**
 * The status of the book. (Abstract class)
 * @author Xiaohuo(Wang Boyun)
 */
public abstract class BaseStatus
{
    int bookId;
    String location;
    boolean status;

    /**
     * The constructor of the BaseStatus.
     * @param bookId The BookId in database.
     * @param location The location of the book.
     * @param status The status of the book. (Revert or not revert)
     */
    public BaseStatus(int bookId, String location, boolean status)
    {
        this.bookId = bookId;
        this.location = location;
        this.status = status;
    }

    public String getLocation()
    {
        return location;
    }

    public boolean getStatus()
    {
        return status;
    }

    public int getBookId()
    {
        return bookId;
    }
}
