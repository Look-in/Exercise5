package by.entity.comparator;


import by.entity.event.AttributeToCompare;
import by.entity.event.Item;

import java.util.ArrayList;
import java.util.Collections;

public class ItemComparator  {

    public static void compare(ArrayList<Item> item, AttributeToCompare sortBy) {
        switch (sortBy) {
            case PRICE:
                Collections.sort(item, (o1, o2) -> (int) (o1.getPrice() - o2.getPrice()));
                break;
            case PRICEDESC:
                Collections.sort(item, (o1, o2) -> (int) (o2.getPrice() - o1.getPrice()));
                break;
            case NAME:
                Collections.sort(item, (o1, o2) -> (o1.getName().compareToIgnoreCase(o2.getName())));
                break;
            case STATUS:
                Collections.sort(item, (o1, o2) -> (o1.getItemStatus().getItemStatusId() - o2.getItemStatus().getItemStatusId()));
                break;
        }
    }
}
