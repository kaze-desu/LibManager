package com.xiaohuo.libmanager.Controller.BookManager.Resource;

public class BooksImpl implements Books {
    private int isbn;
    private int key;
    private String tittle;
    private String author;
    private String theme;
    private String category;
    private String describe;
    private String catalog;
    private String format;
    @Override
    public String getTittle(int isbn) {
        isbn = this.isbn;

        return tittle;
    }

    @Override
    public String getAuthor(int isbn) {
        isbn = this.isbn;
        return author;
    }

    @Override
    public String getTheme(int isbn) {
        isbn = this.isbn;
        return theme;
    }

    @Override
    public String getCategory(int isbn) {
        isbn = this.isbn;
        return category;
    }

    @Override
    public String getDescribe(int isbn) {
        isbn = this.isbn;
        return describe;
    }

    @Override
    public String getCatalog(int isbn) {
        isbn = this.isbn;
        return catalog;
    }

    @Override
    public String getFormat(int isbn) {
        isbn = this.isbn;
        return format;
    }

    @Override
    public int getIsbn(int key) {
        key = this.key;

        return isbn;
    }
    private void getBook(int isbn) {
        // TODO Auto-generated method stub
        // Get the book from database
        // Get the book's information
        // Set the book's information to the class

    }
}
