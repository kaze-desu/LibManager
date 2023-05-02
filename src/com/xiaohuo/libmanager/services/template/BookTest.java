package com.xiaohuo.libmanager.services.template;

import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BooksManagerService;
import com.xiaohuo.libmanager.services.BaseBooks;
import org.junit.jupiter.api.Test;
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
        for (int i = 0;i<1000;i++)
        {
            books.add(new Book("tittle1","author1","publisher1","category1","isbn1"));
            books.add(new Book("tittle2","author2","publisher2","category2","isbn2"));
            books.add(new Book("tittle3","author3","publisher3","category3","isbn3"));
            books.add(new Book("tittle4","author4","publisher4","category4","isbn4"));
            books.add(new Book("tittle5","author5","publisher5","category5","isbn5"));
            books.add(new Book("tittle6","author6","publisher6","category6","isbn6"));
        }
        journals.add(new Journal("tittle1","author1","publisher1","category1","issn1"));
        journals.add(new Journal("tittle2","author2","publisher2","category2","issn2"));
        journals.add(new Journal("tittle3","author3","publisher3","category3","issn3"));
        journals.add(new Journal("tittle4","author4","publisher4","category4","issn4"));
        journals.add(new Journal("tittle5","author5","publisher5","category5","issn5"));
        journals.add(new Journal("tittle6","author6","publisher6","category6","issn6"));
        newspapers.add(new Newspaper("tittle1","author1","publisher1","category1","issn1","date1"));
        newspapers.add(new Newspaper("tittle2","author2","publisher2","category2","issn2","date2"));
        newspapers.add(new Newspaper("tittle3","author3","publisher3","category3","issn3","date3"));
        newspapers.add(new Newspaper("tittle4","author4","publisher4","category4","issn4","date4"));
        newspapers.add(new Newspaper("tittle5","author5","publisher5","category5","issn5","date5"));
        newspapers.add(new Newspaper("tittle6","author6","publisher6","category6","issn6","date6"));


        BooksManagerService booksManagerService = new BooksManagerService();
        booksManagerService.add(books);
        booksManagerService.add(journals);
        booksManagerService.add(newspapers);
    }
}