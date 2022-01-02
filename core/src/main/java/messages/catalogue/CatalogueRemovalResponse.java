package messages.catalogue;

public class CatalogueRemovalResponse {
    private boolean successfulAction = false;
    private int userId;

    public CatalogueRemovalResponse () {}

    public CatalogueRemovalResponse (int userId, boolean successfulAction) {
        this.userId = userId;
        this.successfulAction = successfulAction;
    }

    public boolean isSuccessfulAction() {
        return successfulAction;
    }

    public void setSuccessfulAction(boolean successfulAction) {
        this.successfulAction = successfulAction;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
