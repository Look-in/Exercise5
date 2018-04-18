package by.dao.old;

import by.entity.old.ItemType;

import java.util.List;

public interface SelectItemType {

     List<ItemType> readItemTypes();

     String readItemType(int itemStatusId);
}