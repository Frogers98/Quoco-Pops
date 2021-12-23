package messages.borrow;

import messages.MySerializable;

public class BorrowAddition implements MySerializable {
    private int bookID;
    private int numCopies;

    public CatalogueAddition(int userID, int bookID, int numCopies) {
        this.bookID = bookID;
        this.numCopies = numCopies;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getNumCopies() {
        return numCopies;
    }

    public void setNumCopies(int numCopies) {
        this.numCopies = numCopies;
    }
}
