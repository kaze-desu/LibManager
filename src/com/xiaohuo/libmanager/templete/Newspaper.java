package com.xiaohuo.libmanager.templete;

import com.xiaohuo.libmanager.Services.BooksBase;

import java.util.ArrayList;

/**
 * This class is a subclass of BooksBase, which is used to store the information of a newspaper.
 * It has two attributes: issn and copyRight.
 * It has a constructor and a method to get the information of a newspaper.
 * @see com.xiaohuo.libmanager.Services.BooksBase
 * @author xiaohuo(WANG BOYUN)
 */
public class Newspaper extends BooksBase
{
    private String issn;
    private String copyRight;
    public Newspaper(String tittle, String author, String publisher, String category,String issn,String copyRight)
    {
        super(tittle, author, publisher, category);
        this.issn = issn;
        this.copyRight = copyRight;
    }

    /**
     * This method is used to get the information of a newspaper.
     * @return an ArrayList of String, which contains the information of a newspaper.
     */
    @Override
    public ArrayList<String> getBookInfo()
    {
        ArrayList<String> newspaperInfo = super.getBookInfo();
        newspaperInfo.add(issn);
        newspaperInfo.add(copyRight);
        return newspaperInfo;
    }
}
