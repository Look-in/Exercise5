package by.entity.old;

public class ItemType {
    private int itemTypeId;
    private String itemType;

    public ItemType(int itemTypeId, String itemType) {
        this.itemTypeId = itemTypeId;
        this.itemType = itemType;
    }

    public ItemType(int itemTypeId) {
        this.itemTypeId = itemTypeId;
       // itemType = ItemFactory.getItemTypeDao().readItemType(itemTypeId);
    }

    public ItemType() {
    }

    public void setItemType(int itemTypeId) {
        this.itemTypeId = itemTypeId;
      //  itemType = ItemFactory.getItemTypeDao().readItemType(itemTypeId);
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public String getItemType() {
        return itemType;
    }

    @Override
    public String toString() {
        return itemTypeId + " " + itemType;
    }
}

