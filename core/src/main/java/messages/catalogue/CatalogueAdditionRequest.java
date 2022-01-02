package messages.catalogue;

import core.Book;
import messages.MySerializable;

public class CatalogueAdditionRequest implements MySerializable {
    String libraryRef;
    Book book;

    public CatalogueAdditionRequest() {}

    public CatalogueAdditionRequest(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getLibraryRef() {
        return this.libraryRef;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
    }

}
