package com.xiaohuo.libmanager.services.template;

/**
 * @author Xiaohuo (Wang Boyun)
 */

public enum TypeList
{
    /**
     * This is the enum of the type of books.
     * If you want to add a new template, you should add a new enum here.
     */

    BOOK {
        @Override
        public String getType()
        {
            return "Book";
        }
        @Override
        public int elements() {
            return 5;
        }
    }
    ,
    JOURNAL{
@Override
public String getType(){
    return "Journal";
}
@Override
public int elements() {
    return 5;
}
    },
    NEWSPAPER{
        @Override
        public String getType(){
            return "Newspaper";
        }
        @Override
        public int elements() {
            return 6;
        }
    };
    public abstract int elements();
    public abstract String getType();
}

