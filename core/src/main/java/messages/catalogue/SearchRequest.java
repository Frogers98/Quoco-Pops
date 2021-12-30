package messages.catalogue;
import messages.core.MySerializable;
public class SearchRequest implements MySerializable {
    // Class to represent a search request to the Catalogue Service containing a book ID
    private int bookId;
    private int searchId;

    public SearchRequest() {}

    public SearchRequest(int bookId, int searchId) {
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
}
