package messages.catalogue;
import messages.MySerializable;

import core.Book;

public class SearchResponse implements MySerializable {
    private Book book;
    private int userId;

    public SearchResponse() {}

    public SearchResponse(Book book, int userId) {
        this.book = book;
        this.userId = userId;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
