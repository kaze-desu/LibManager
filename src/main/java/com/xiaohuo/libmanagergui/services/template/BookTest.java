package com.xiaohuo.libmanagergui.services.template;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.BooksManageServiceImpl;
import com.xiaohuo.libmanagergui.services.BaseBooks;
import org.junit.jupiter.api.Test;

//import org.junit.Test;
import java.util.ArrayList;


class BookTest
{
    /**
     * A test class.
     * Nothing here.
     */
    @Test
    void getBookInfo() throws CollectionException
    {

        ArrayList<BaseBooks> books = new ArrayList<>();
        ArrayList<BaseBooks> journals = new ArrayList<>();
        ArrayList<BaseBooks> newspapers = new ArrayList<>();
/*        for (int i = 0; i < 1000; i++)
        {
            books.add(new Book("title" + i, "author" + i, "publisher" + i, "category" + i, "isbn" + i));
            journals.add(new Journal("title" + i, "author" + i, "publisher" + i, "category" + i, "issn" + i));
            newspapers.add(new Newspaper("title" + i, "author" + i, "publisher" + i, "category" + i, "issn" + i, "date" + i));
        }*/
        books.add(new Book("title1","author1","publisher1","category1","isbn1"));
        books.add(new Book("title2","author2","publisher2","category2","isbn2"));

        journals.add(new Journal("title1","author1","publisher1","category1","issn1"));
        journals.add(new Journal("title2","author2","publisher2","category2","issn2"));

        newspapers.add(new Newspaper("title1","author1","publisher1","category1","issn1","date1"));
        newspapers.add(new Newspaper("title2","author2","publisher2","category2","issn2","date2"));



        BooksManageServiceImpl booksManageServiceImpl = new BooksManageServiceImpl();
        booksManageServiceImpl.add(books);
        booksManageServiceImpl.add(journals);
        booksManageServiceImpl.add(newspapers);
    }
}