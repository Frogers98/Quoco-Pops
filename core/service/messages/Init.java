package service.messages;

import service.core.LibraryService;


public class Init {
    
    private LibraryService service;
  
    public Init(LibraryService service) {
        this.service = service;
    }
  
    public LibraryService getService() {
        return service;
    }
}



