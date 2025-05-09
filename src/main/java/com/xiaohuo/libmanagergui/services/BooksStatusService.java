package com.xiaohuo.libmanagergui.services;

import com.xiaohuo.libmanagergui.exception.CollectionException;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public interface BooksStatusService
{
    /**
     * Add the book status to the database.
     * @param type The book type.
     * @param identityCode The identity code of the book.(ISBN/ISSN)
     * @param location The location of the book.
     * @param status The status of the book.
     * @throws CollectionException If the book is not exist, throw the exception.
     */
    void addBookStatus(String type, String identityCode, String location,boolean status) throws CollectionException;

    /**
     * Check the book type is exist or not.
     * @param type The book type.
     * @return If the book type is exist, return true, else return false.
     * @throws CollectionException If the book is not exist, throw the exception.
     */
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
    /**
     * Search the book status by book type and identity code.
     * Convert all the data to string and return it.
     *
     * @param type         The book type.
     * @param identityCode The identity code of the book.
     * @return Integer is statusId, String is the location of the book.
     * @throws CollectionException If the book is not exist, throw the exception.
     */

    Map<Integer, BooksStatusServiceImpl.StatusData> searchAllBookStatus(String type, String identityCode) throws CollectionException;

    /**
     * Get all the statusId;
     * @param list The map of statusId and location.
     * @return The list of statusId.
     */
    ArrayList<Integer> getStatusId(Map<Integer,String>list);
    /**
     * Edit the location of the book.
     * @param statusId The statusID of the book.
     * @param location The location of the book.
     * @throws CollectionException If the book is not exist, throw the exception.
     */
    void editBookLocation(int statusId,String location) throws CollectionException;

    /**
     * Change the status of the book to borrow.
     * @param statusId The statusID of the book.
     * @throws CollectionException If the book is not exist, throw the exception.
     */
    void borrowBook(int statusId) throws CollectionException;

    /**
     * Change the status of the book to revert.
     * @param statusId The statusID of the book.
     * @throws CollectionException If the book is not exist, throw the exception.
     */
    void revertBook(int statusId) throws CollectionException;

    /**
     * Delete the book status by statusId.
     * @param statusId The statusID of the book.
     * @throws CollectionException If the book is not exist, throw the exception.
     */
    void deleteBookStatus(int statusId) throws CollectionException;
}
