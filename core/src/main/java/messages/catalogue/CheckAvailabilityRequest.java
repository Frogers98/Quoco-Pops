package messages.catalogue;

public class CheckAvailabilityRequest {
    private String libraryRef;
    private int bookId;

    public CheckAvailabilityRequest() {}

    public CheckAvailabilityRequest(String libraryRef, int bookId) {
        this.libraryRef = libraryRef;
        this.bookId = bookId;
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

    
}
