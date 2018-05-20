package by.Utils;

import by.entity.Race;
import by.service.reference.AttributeToCompare;

import java.util.List;

/**
 * Класс для сортировки списка забегов.
 *
 * @author Serg Shankunas
 */
public class RaceSortUtil {

    public static void compare(List<Race> races, AttributeToCompare sortBy) {
        switch (sortBy) {
            case NAME:
                races.sort((o1, o2) -> (o1.getRace().compareToIgnoreCase(o2.getRace())));
                break;
            case NAME_DESC:
                races.sort((o1, o2) -> (o2.getRace().compareToIgnoreCase(o1.getRace())));
                break;
            case ID:
                races.sort((o1, o2) -> (o1.getId() - o2.getId()));
                break;
        }
    }
}
