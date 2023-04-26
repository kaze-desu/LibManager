package com.xiaohuo.libmanager.services.template;

import com.xiaohuo.libmanager.services.BaseBooks;

import java.util.ArrayList;

/**
 * @Type: Journal
 * @see BaseBooks
 * @author xiaohuo(WANG BOYUN)
 */
public class Journal extends BaseBooks
{
    private final String type = "Journal";
    private final String issn;
    /**
     * This is the constructor of Journal, which is used to initialize the information of a journal.
     */
    public Journal(String tittle, String author, String publisher, String category,String issn)
    {
        super(tittle, author, publisher, category);
        this.issn = issn;
        setType(type);
    }

    /**
     * This method is used to get the information of a journal.
     * @return an ArrayList of String(Not a BaseBooks!), which contains the information of a journal.
     */
    @Override
    public ArrayList<String> getBookInfo()
    {
        ArrayList<String> journal = super.getBookInfo();
        journal.add(issn);
        return journal;
    }
    /**
     * This method is used to get the ISSN of a journal.
     * @return the ISSN of a journal.
     */
    public String getIssn()
    {
        return issn;
    }
}
