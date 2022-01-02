package messages.catalogue;

import messages.MySerializable;

public class AvailableLocallyResponse implements MySerializable {
    private String libraryRef;
    private int bookId;
    private int copiesAvailable;

    public AvailableLocallyResponse() {}

    public AvailableLocallyResponse(String libraryRef, int BookId, int copiesAvailable) {
        this.libraryRef = libraryRef;
        this.bookId = BookId;
        this.copiesAvailable = copiesAvailable;
    }

    public String getLibraryRef() {
        return this.libraryRef;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
    }

    public int getBookId() {
        return this.bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getCopiesAvailable() {
        return this.copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }   
}
