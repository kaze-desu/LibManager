package com.xiaohuo.libmanager.dao;

import com.xiaohuo.libmanager.Init;
import com.xiaohuo.libmanager.db.DatabaseClose;
import com.xiaohuo.libmanager.db.DatabaseConnect;
import com.xiaohuo.libmanager.exception.CollectionException;
import com.xiaohuo.libmanager.services.BaseBooks;
import com.xiaohuo.libmanager.services.template.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiaohuo (Wang Boyun)
 */


public class AddDao
{
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private String table = "BookList";
    private boolean init = false;
    public void initTable() throws CollectionException
    {

        List<Throwable> exceptions = new ArrayList<>();
        conn = DatabaseConnect.connect();
        String sql = "CREATE TABLE IF NOT EXISTS ? (BookID INT PRIMARY KEY AUTO_INCREMENT,Tittle VARCHAR(255) NOT NULL,Author VARCHAR(255) NOT NULL,Publisher VARCHAR(255) NOT NULL,Category VARCHAR(255) NOT NULL)";
        try
        {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,table);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }finally
        {
            DatabaseClose.close(pstmt,conn);
        }
        init = true;
    }
    public void add()
    {
        //TODO 重构整个AddDao，考虑如何使用一个主体结构，搭配传参来完成不同的分类请求，根据目前的代码，可知如果把每个分类都写一个方法予以实现的话，重复度过高，完全没有必要
        //TODO 需要思考一个传参便捷的方法，以及兼顾分类

    }
    public void addBook(ArrayList<BaseBooks> booksInfo) throws CollectionException
    {
        List<Throwable> exceptions = new ArrayList<>();
        if (!init)
        {
            exceptions.add(new Exception("Table not initiated, please call initTable() first."));
            throw new CollectionException(exceptions);
        }
        String type = "Book";
        conn = DatabaseConnect.connect();
        String sql = "SHOW COLUMNS FROM ? LIKE '?'";
        try
        {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        try
        {
            pstmt.setString(1,table);
            pstmt.setString(2,"Isbn");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        try
        {
            if(pstmt.executeUpdate()<1)
            {
                sql = "ALTER TABLE ? ADD Isbn VARCHAR(255) NOT NULL";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,table);
                pstmt.executeUpdate();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            exceptions.add(e);
        }
        for (BaseBooks baseBooks : booksInfo)
        {
            Book book = (Book) baseBooks;
            sql = "INSERT INTO ? (Tittle,Author,Category,Isbn) VALUES (?,?,?,?)";
            try
            {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,table);
                pstmt.setString(2,book.getTittle());
                pstmt.setString(3,book.getAuthor());
                pstmt.setString(4,book.getCategory());
                pstmt.setString(5,book.getIsbn());
                pstmt.executeUpdate();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                exceptions.add(e);
            }
            finally
            {
                DatabaseClose.close(pstmt,conn);
            }
            if(exceptions.size()>0)
            {
                throw new CollectionException(exceptions);
            }
        }

    }
    public void addJournal(String tittle,String author,String publisher,String category,String issn)
    {

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
