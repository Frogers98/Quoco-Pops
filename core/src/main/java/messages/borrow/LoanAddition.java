package messages.borrow;

import messages.MySerializable;

public class LoanAddition implements MySerializable {
    private int loanID;
    private int userID;
    private int bookID;
    private String bookTitle;
    private String libraryName;
    private int numCopies;

    public LoanAddition(int loanID, int userID, int bookID, int numCopies, String libraryName, String bookTitle) {
        this.userID = userID;
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.libraryName = libraryName;
        this.numCopies = numCopies;
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

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public int getNumCopies() {
        return numCopies;
    }

    public void setNumCopies(int numCopies) {
        this.numCopies = numCopies;
    }
}
