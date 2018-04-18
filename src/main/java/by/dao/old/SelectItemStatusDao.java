package by.dao.old;

import by.entity.old.ItemStatus;

import java.util.List;

public interface SelectItemStatusDao {

     List<ItemStatus> readItemStatuses();

     String readItemStatus(int itemStatusId);
}
