package com.xiaohuo.libmanagergui.services;

import com.xiaohuo.libmanagergui.exception.CollectionException;

import java.util.ArrayList;
import java.util.Map;

interface BooksManageService
{

    /* Add function part */

    /**
     * The add function will transfer data to DAO, so it's not solving data, only transfer data.
     * @param booksInfo This param is a ArrayList that type of BaseBooks. For safety reason, I use a function (just like book.getXXX) to get unique value from each different extend class.
     *                 As you see, is not quite beautiful isn't it ?
     *                  4.29: Solved, so concise.
     */
    void add(ArrayList<BaseBooks> booksInfo) throws CollectionException;
    /* Search function part */

    /**
     * Search the book by general search mod.
     * @return a Map of books information. Integer: the BookID of the book. ArrayList: the information of the book.
     * @throws CollectionException Exception thrown when there is any error.
     */
    Map<Integer, ArrayList<String>> search(String value) throws CollectionException;

    /**Search by type.
     * @param type The type of the book.
     * @return a Map of books information. Integer: the BookID of the book. ArrayList: the information of the book.
     * @throws CollectionException Exception thrown when there is any error.
     */
    Map<Integer, ArrayList<String>>searchByType(String type) throws CollectionException;

    /**
     * Search by author.
     * @param author The author of the book.
     * @return a Map of books information. Integer: the BookID of the book. ArrayList: the information of the book.
     * @throws CollectionException Exception thrown when there is any error.
     */
    Map<Integer, ArrayList<String>>searchByAuthor(String author) throws CollectionException;

    /**
     * Search by publisher.
     * @param publisher The publisher of the book.
     * @return a Map of books information. Integer: the BookID of the book. ArrayList: the information of the book.
     * @throws CollectionException Exception thrown when there is any error.
     */
    Map<Integer, ArrayList<String>>searchByPublisher(String publisher) throws CollectionException;

    /**
     *  Search by category.
     * @param category The category of the book.
     * @return a Map of books information. Integer: the BookID of the book. ArrayList: the information of the book.
     * @throws CollectionException Exception thrown when there is any error.
     */
    Map<Integer, ArrayList<String>>searchByCategory(String category) throws CollectionException;

    /**
     * Search by identity Code.
     * @param identityType The type of the book.
     * @param identityCode The identity code(ISSN/ISBN) of the book.
     * @return A arraylist of the book information, without book id.
     * @throws CollectionException If the book is not exist, throw the exception.
     */
    ArrayList<String> searchByIdentityCode(String identityType,String identityCode) throws CollectionException;
    /**
     * A filter by using identify code.
     * Search the book by type and filter the book by identity code.
     * @param identityType The book type.
     * @param identityCode The identity code(ISSN/ISBN) of the book.
     * @return The book id.
     * @throws CollectionException If the book is not exist, throw the exception.
     */
    int getBookId(String identityType, String identityCode) throws CollectionException;

    /**
     * If the user don't know the ISBN OR ISSN of the book they want to delete, this method will search the
     * book first and return a list contain all the ISSN or ISBN of the target book
     * @param title the title of the book
     * @return a list contain all the ISSN or ISBN of the target book
     * @throws CollectionException if the book is not exist
     */
    ArrayList<String> getBookByTitleAndType(String title, String type)  throws CollectionException;

    void deleteBookByIdentityCode(String type,String code, ArrayList<Integer> statusID) throws CollectionException;

    void editBookInformation(String column,String targetContent,String type,String code) throws CollectionException;
}
