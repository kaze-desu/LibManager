package com.xiaohuo.libmanager.services;

import com.xiaohuo.libmanager.exception.CollectionException;

import java.util.Map;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public interface BooksStatusService
{
    void addBookStatus(String type, String identityCode, String location,boolean status) throws CollectionException;
    boolean checkBookType(String type) throws CollectionException;

    /**
     * Search the book status by book type and identity code.
     * Only return the book that is revert.
     * Convert all the data to string and return it.
     * @param type The book type.
     * @param identityCode The identity code of the book.
     * @return Integer is statusId, String is the location of the book.
     * @throws CollectionException If the book is not exist, throw the exception.
     */
    Map<Integer,String> searchBookStatus(String type, String identityCode) throws CollectionException;
}
