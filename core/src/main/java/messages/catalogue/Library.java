package messages.catalogue;

import messages.MySerializable;

// (id, library_ref, place_id, library_name)
public class Library implements MySerializable{


    private int id;
    private String library_ref;
    private String place_id;
    private String library_name;

    public Library() {}

    public Library(int id, String library_ref, String place_id, String library_name) {
        this.id = id;
        this.library_ref = library_ref;
        this.place_id = place_id;
        this.library_name = library_name;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibrary_ref() {
        return library_ref;
    }

    public void setLibrary_ref(String library_ref) {
        this.library_ref = library_ref;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getLibrary_name() {
        return library_name;
    }

    public void setLibrary_name(String library_name) {
        this.library_name = library_name;
    }
}
