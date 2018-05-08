package by.entity.event;


import by.Utils.SetException;
import by.entity.old.ItemStatus;
import by.entity.old.ItemType;

public class Item {
    private int itemId;
    private float price;
    private String name;
    private String description;
    private byte[] image;
    private ItemStatus itemStatus;
    private ItemType itemType;

    public Item(int itemId, float price, String name, String description, ItemStatus itemStatus, ItemType itemType) {
        this.itemStatus = itemStatus;
        this.itemType=itemType;
        this.itemId = itemId;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public Item() {
        itemStatus=new ItemStatus();
        itemType=new ItemType();
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public void setItemType(int itemTypeId) {
        itemType.setItemType(itemTypeId);
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

     public void setItemStatus(int itemStatusId) {
        itemStatus.setItemStatus(itemStatusId);
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getItemId() {
        return itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    // @Test
    public void setPrice(float price) {
        if (price <= 0) throw new SetException("Error. Price not specified");
        else this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }


    @Override
    public String toString() {
        return "Item [Id =" + itemId + ", price=" +
                String.format("%.2f", price) + ", name=" + name + " description= " +
                description + " status=" + itemStatus.toString() + " type=" + itemType.toString();
    }
}







