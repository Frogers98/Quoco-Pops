package messages.borrow;

public class ReturnBookRequest {
    private int loanId;
    private String libraryRef;
    private int bookId;
    private int memberId;

    public ReturnBookRequest() {}

    public ReturnBookRequest(int loanId, String libraryRef, int bookId, int memberId) {
        this.libraryRef = libraryRef;
        this.bookId = bookId;
    }

    public String getLibraryRef() {
        return this.libraryRef;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanID(int loanId) {
        this.loanId = loanId;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
    }

    public int getBookID() {
        return bookId;
    }

    public void setBookID(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    
}
