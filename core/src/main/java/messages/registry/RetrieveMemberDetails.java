package messages.registry;

import messages.MySerializable;

public class RetrieveMemberDetails implements MySerializable {
    private int id;

    public RetrieveMemberDetails() {}

    public RetrieveMemberDetails(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
}
