package by.service.reference;

/**
 * Attributes for sorting List of Races.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public enum AttributeToCompare {
    ID("Идент"),
    NAME("&#8593 Наименование"),
    NAME_DESC("&#8595 Наименование");


    private final String displayName;

    AttributeToCompare(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}