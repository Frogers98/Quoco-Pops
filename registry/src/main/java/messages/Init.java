package messages;

import registry.RegistryService;

public class Init {
    private RegistryService registryService;
    
    public Init() {}

    public Init(RegistryService registryService) {
        this.registryService = registryService;
    }

    public RegistryService getRegistryService() {
        return this.registryService;
    }

    public void setRegistryService(RegistryService registryService) {
        this.registryService = registryService;
    }
}
