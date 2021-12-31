package messages.catalogue;

public class SearchRequest {
    // Class to represent a search request to the Catalogue Service containing a book ID
    private String libraryRef;
    private int bookId;
    private int searchId;

    public SearchRequest() {}

    public SearchRequest(String libraryRef, int bookId, int searchId) {
        this.libraryRef = libraryRef;
        this.bookId = bookId;
        this.searchId = searchId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setSearchId(int searchId) {
        this.searchId = searchId;
    }

    public int getSearchId() {
        return searchId;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
    }

    public String getLibraryRef() {
        return libraryRef;
    }
}
