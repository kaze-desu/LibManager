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
/*        for (int i = 0; i < 1000; i++)
        {
            books.add(new Book("tittle" + i, "author" + i, "publisher" + i, "category" + i, "isbn" + i));
            journals.add(new Journal("tittle" + i, "author" + i, "publisher" + i, "category" + i, "issn" + i));
            newspapers.add(new Newspaper("tittle" + i, "author" + i, "publisher" + i, "category" + i, "issn" + i, "date" + i));
        }*/
        books.add(new Book("tittle1","author1","publisher1","category1","isbn1"));
        books.add(new Book("tittle2","author2","publisher2","category2","isbn2"));

        journals.add(new Journal("tittle1","author1","publisher1","category1","issn1"));
        journals.add(new Journal("tittle2","author2","publisher2","category2","issn2"));

        newspapers.add(new Newspaper("tittle1","author1","publisher1","category1","issn1","date1"));
        newspapers.add(new Newspaper("tittle2","author2","publisher2","category2","issn2","date2"));



        BooksManagerService booksManagerService = new BooksManagerService();
        booksManagerService.add(books);
        booksManagerService.add(journals);
        booksManagerService.add(newspapers);
    }
}