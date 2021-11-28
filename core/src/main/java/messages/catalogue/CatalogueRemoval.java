package messages.catalogue;

public class CatalogueRemoval {
    private int bookID;
    private String libraryName;

    public CatalogueRemoval() {}

    public CatalogueRemoval(int bookID, String libraryName) {
        this.bookID = bookID;
        this.libraryName = libraryName;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLibraryName() {
        return libraryName;
    }
}
