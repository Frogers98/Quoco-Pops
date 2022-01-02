package messages.catalogue;

import java.util.HashMap;

public class AvailableRemotelyResponse {
    String libraryRef;
    int bookId;
    HashMap<String, Integer> whereAvailable;

    public AvailableRemotelyResponse() {}

    public AvailableRemotelyResponse(String libraryRef, int bookId, HashMap<String, Integer> whereAvailable) {
        this.libraryRef = libraryRef;
        this.bookId = bookId;
        this.whereAvailable = whereAvailable;
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

    public HashMap<String, Integer> getWhereAvailable() {
        return this.whereAvailable;
    }

    public void setWhereAvailable(HashMap<String, Integer> whereAvailable) {
        this.whereAvailable = whereAvailable;
    }

}
