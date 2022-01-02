package messages.borrow;

import messages.MySerializable;

public class CalculateFinesRequest implements MySerializable {
    private String libraryRef;
    private int id;

    public CalculateFinesRequest() {}

    public CalculateFinesRequest(String libraryRef, int id) {
        this.id = id;
        this.libraryRef = libraryRef;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibraryRef() {
        return this.libraryRef;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
    }
    
}
