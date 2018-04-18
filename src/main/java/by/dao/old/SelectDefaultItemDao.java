package by.dao.old;

import by.entity.event.Item;

import java.util.ArrayList;

public interface SelectDefaultItemDao {

    ArrayList<Item> readListItem(int typeItemId);

    ArrayList<Item> readListItem();

    void readItem(Item item);

}