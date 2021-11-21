package messages.catalogue;

public class SearchRequest {
    // Class to represent a search request to the Catalogue Service containing a book ID
    private long bookId;
    private long userId;

    public SearchRequest() {}

    public void setBookId(long bookId) {this.bookId = bookId;}

    public long getBookId() {return bookId;}

    public void setUserId(long userId) {this.userId = userId;}

    public long getUserId() {return userId;}
}
