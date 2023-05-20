package com.xiaohuo.libmanagergui.services.status;

import com.xiaohuo.libmanagergui.services.BaseStatus;

/**
 * The status of the book.
 * @author Xiaohuo(Wang Boyun)
 */
public class Status extends BaseStatus
{

    public Status(int bookId, String location, boolean status)
    {
        super(bookId, location, status);
    }
}
