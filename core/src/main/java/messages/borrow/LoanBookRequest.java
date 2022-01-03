package messages.borrow;

import messages.MySerializable;

public class LoanBookRequest implements MySerializable {
    private String libraryRef;
    private int loanID;
    private int userID;
    private int bookID;
    private String loanDate;
    private String returnDate;
    private int finesOwed;


    public LoanBookRequest(String libraryRef, int loanID, int userID, int bookID, String loanDate, String returnDate, int finesOwed) {
        this.libraryRef = libraryRef;
        this.loanID = loanID;
        this.userID = userID;
        this.bookID = bookID;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.finesOwed = finesOwed;
    }

    public String getLibraryRef() {
        return this.libraryRef;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
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

    public int getFinesOwed() {
        return this.finesOwed;
    }

    public void setFinesOwed(int finesOwed) {
        this.finesOwed = finesOwed;
    }
}

