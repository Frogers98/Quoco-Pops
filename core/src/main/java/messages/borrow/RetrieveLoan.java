package messages.borrow;

import messages.MySerializable;

public class RetrieveLoan implements MySerializable {
    private int loanID;
    private int userID;


    public RetrieveLoan(int loanID, int userID) {
        this.loanID = loanID;
        this.userID = userID;
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
}
