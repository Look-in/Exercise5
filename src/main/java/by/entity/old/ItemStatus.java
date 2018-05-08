package by.entity.old;

public class ItemStatus {

    private int itemStatusId;

    private String itemStatus;

    public ItemStatus(int itemStatusId, String itemStatus) {
        this.itemStatusId = itemStatusId;
        this.itemStatus = itemStatus;
    }

    public ItemStatus(int itemStatusId) {
        this.itemStatusId = itemStatusId;
       // itemStatus = ItemFactory.getItemStatusDao().readItemStatus(itemStatusId);
    }

    public ItemStatus() {
    }

    public int getItemStatusId() {
        return itemStatusId;
    }

    public void setItemStatus(int itemStatusId) {
        this.itemStatusId=itemStatusId;
       // itemStatus=ItemFactory.getItemStatusDao().readItemStatus(itemStatusId);
    }

    public String getItemStatus() {
        return itemStatus;
    }

    @Override
    public String toString(){
        return itemStatusId+ " "+itemStatus;
    }
}
