package messages.borrow;

public class SearchResponse {
    private int loanID;
    private int userID;
    private int bookID;
    private String loanDate;
    private String returnDate;


    public SearchResponse(int loanID, int userID, int bookID, String loanDate, String returnDate) {
        this.loanID = loanID;
        this.userID = userID;
        this.bookID = bookID;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }
    public int getLoanID() {
        return loanID;
    }
    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
    public String getLoanDate() {
        return loanDate;
    }
    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }
    public String getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
