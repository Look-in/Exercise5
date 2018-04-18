package by.entity.event;

public enum AttributeToCompare{
    PRICE("Price"),
    PRICEDESC("Price DESC"),
    NAME("Name"),
    STATUS("Status");

    private final String displayName;

    private AttributeToCompare (String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
