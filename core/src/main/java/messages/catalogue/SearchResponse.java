package messages.catalogue;
import messages.core.MySerializable;

public class SearchResponse implements MySerializable {
    private int bookID;
    private String bookTitle;
    private String bookAuthor;
    private String libraryName;
    private int numCopies;
    private int totalCopies;
    private int userId;

    private int searchId;

    public SearchResponse() {};

    public SearchResponse(int bookID, String bookTitle, String bookAuthor, String libraryName, int numCopies, int totalCopies, int searchId) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.libraryName = libraryName;
        this.numCopies = numCopies;
        this.totalCopies = totalCopies;
        this.searchId = searchId;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setNumCopies(int numCopies) {
        this.numCopies = numCopies;
    }

    public int getNumCopies() {
        return numCopies;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setSearchId(int searchId) {
        this.searchId = searchId;
    }

    public int getSearchId() {
        return searchId;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getTotalCopies() {
        return totalCopies;
    }
}
