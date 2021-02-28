package Project1.Enums;

public enum ToolsStatus {
    AVAILABLE ("Available"),
    NOT_AVAILABLE("Not Available");

    private String status;

    ToolsStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
