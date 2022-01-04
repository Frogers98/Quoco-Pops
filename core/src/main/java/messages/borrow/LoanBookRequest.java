package messages.borrow;

import messages.MySerializable;

public class LoanBookRequest implements MySerializable {
    private String libraryRef;
    private int userID;
    private int bookID;
    private String loanDate;
    private String returnDate;

    public LoanBookRequest(int userID, int bookID, String loanDate, String returnDate, String libraryRef) {
        this.libraryRef = libraryRef;
        this.userID = userID;
        this.bookID = bookID;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public String getLibraryRef() {
        return this.libraryRef;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
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

