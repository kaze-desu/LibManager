package com.xiaohuo.libmamnager.Controller.BookManager.Resource;

public interface Books {
    /**
     * Get the title of a book
     * @param isbn The only identification of a book
     * @return The title of the book
     */
    String getTittle(int isbn);
    /**
     * Get the author of a book
     * @param isbn The only identification of a book
     * @return The author of the book
     */
    String getAuthor(int isbn);
    /**
     * Get the theme of a book
     * @param isbn The only identification of a book
     * @return The theme of the book
     */
    String getTheme(int isbn);
    /**
     * Get the category of a book
     * @param isbn The only identification of a book
     * @return The category of the book
     */
    String getCategory(int isbn);
    /**
     * Get the description of a book
     * @param isbn The only identification of a book
     * @return The description of the book
     */
    String getDescribe(int isbn);
    /**
     * Get the format of a book
     * @param isbn The only identification of a book
     * @return The format of the book
     */
    String getCatalog(int isbn);
   /**
     * Get the ISBN of a book
     * @param isbn The only identification of a book
     * @return The ISBN of the book
     */
    String getFormat(int isbn);
    /**
     * Get the ISBN of a book
     * @param key is the only number in database for each book, use key to get a single book and it's ISBN
     * @return The ISBN of the book
     */
    int getIsbn(int key);

}
