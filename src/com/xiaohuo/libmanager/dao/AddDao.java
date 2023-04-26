package com.xiaohuo.libmanager.dao;

/**
 * @author lenovo
 */
public class AddDao
{
    public void addBook(String tittle,String author,String publisher,String category,String isbn)
    {
        System.out.println("------------------Book-----------------");
        System.out.println(tittle);
        System.out.println(author);
        System.out.println(publisher);
        System.out.println(category);
        System.out.println(isbn);
        System.out.println("-----------------End-------------------");
    }
    public void addJournal(String tittle,String author,String publisher,String category,String issn)
    {
        System.out.println("----------------Journal----------------");
        System.out.println(tittle);
        System.out.println(author);
        System.out.println(publisher);
        System.out.println(category);
        System.out.println(issn);
        System.out.println("-----------------End-------------------");
    }
    public void addNewspaper(String tittle,String author,String publisher,String category,String issn,String copyRight)
    {
        System.out.println("----------------Newspaper----------------");
        System.out.println(tittle);
        System.out.println(author);
        System.out.println(publisher);
        System.out.println(category);
        System.out.println(issn);
        System.out.println(copyRight);
        System.out.println("-----------------End-------------------");
    }
}
