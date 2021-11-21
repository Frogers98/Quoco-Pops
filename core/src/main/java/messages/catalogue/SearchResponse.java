package messages.catalogue;

public class SearchResponse {
    private boolean bookExists;
    private String libraryRef;

    public boolean isBookExists() {
        return bookExists;
    }

    public void setBookExists(boolean bookExists) {
        this.bookExists = bookExists;
    }

    public String getLibraryRef() {
        return libraryRef;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
    }
}
