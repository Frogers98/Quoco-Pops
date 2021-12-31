package messages.catalogue;
import messages.MySerializable;

import core.Book;

public class SearchResponse implements MySerializable {
    private String libraryRef;
    private Book book;
    private int copiesAvailable;

    public SearchResponse() {}

    public SearchResponse(String libraryRef, Book book, int copiesAvailable) {
        this.libraryRef = libraryRef;
        this.book = book;
        this.copiesAvailable = copiesAvailable;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
    }

    public String getLibraryRef() {
        return libraryRef;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCopiesAvailable() {
        return this.copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }
}
