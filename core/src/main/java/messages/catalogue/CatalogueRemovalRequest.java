package messages.catalogue;

import messages.MySerializable;

public class CatalogueRemovalRequest implements MySerializable{
    private String libraryRef;
    private int bookID;
    
    public CatalogueRemovalRequest() {}

    public CatalogueRemovalRequest(int bookID, String libraryRef) {
        this.bookID = bookID;
        this.libraryRef = libraryRef;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
    }

    public String getLibraryRef() {
        return libraryRef;
    }
}
