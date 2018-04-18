package by.dao.old;

import by.entity.event.Item;

/**
 * <code>DAO</code> For reading atributes of items<br>
 *
 * @author Serg Shankunas
 */

public interface SelectDao<T extends Item>{

    /**
     * Считывает поля объекта Item
     *
     * @param id код товара {@link Item}
     */

    void readItem(T item);

}




