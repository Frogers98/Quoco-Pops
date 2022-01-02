package messages.catalogue;
import messages.MySerializable;

public class SearchRequest implements MySerializable {
    // Class to represent a search request to the Catalogue Service containing a book ID
    private String libraryRef;
    private int bookId;
    private int userId;

    public SearchRequest() {}

    public SearchRequest(String libraryRef, int bookId, int userId) {
        this.libraryRef = libraryRef;
        this.bookId = bookId;
        this.userId = userId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
    }

    public String getLibraryRef() {
        return libraryRef;
    }
}
