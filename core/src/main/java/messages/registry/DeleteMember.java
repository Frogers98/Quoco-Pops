package messages.registry;

import messages.MySerializable;

public class DeleteMember implements MySerializable {
    private int id;

    public DeleteMember() {}

    public DeleteMember(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
