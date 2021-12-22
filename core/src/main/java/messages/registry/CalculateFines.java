package messages.registry;

import messages.MySerializable;

public class CalculateFines implements MySerializable {
    private int id;

    public CalculateFines() {}

    public CalculateFines(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
