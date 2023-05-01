package com.xiaohuo.libmanager.services.template;

import com.xiaohuo.libmanager.services.BaseBooks;

import java.util.ArrayList;

/**
 * @Type: Newspaper
 * @see BaseBooks
 * @author Xiaohuo (Wang Boyun)
 */
public class Newspaper extends BaseBooks

{
    private final String issn;
    private final String copyRight;
    /**
     * This is the constructor of Newspaper, which is used to initialize the information of a newspaper.
     */
    public Newspaper(String tittle, String author, String publisher, String category,String issn,String copyRight)
    {
        super(tittle, author, publisher, category);
        this.issn = issn;
        this.copyRight = copyRight;

        String type = TypeList.NEWSPAPER.getType();
        setType(type);
    }

    /**
     * This method is used to get the information of a newspaper.
     * @return an ArrayList of String(Not a BaseBooks!), which contains the information of a newspaper.
     */
    @Override
    public ArrayList<String> getBookInfo()
    {
        ArrayList<String> newspaperInfo = super.getBookInfo();
        newspaperInfo.add(issn);
        newspaperInfo.add(copyRight);
        return newspaperInfo;
    }
    /**
     * This method is used to get the ISSN of a newspaper.
     * @return the ISSN of a newspaper.
     */
    public String getIssn()
    {
        return issn;
    }
    /**
     * This method is used to get the copyRight of a newspaper.
     * @return the copyRight of a newspaper.
     */
    public String getCopyRight()
    {
        return copyRight;
    }
}
