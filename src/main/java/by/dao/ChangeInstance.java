package dao;

/**
 * <code>DAO</code> For change instances<br>
 *
 * @author Serg Shankunas
 */
public interface ChangeInstance<T> {

    /**
     * Добавляет единицу товара.
     *
     * @param entity сущность
     */
    void create(T entity);

    /**
     * Обновляет позицию товара.
     *
     * @param entity сущность
     */
    void update(T entity);

    /**
     * Удаляет позицию товара.
     *
     * @param id сущности
     */
    void delete(Integer id);
}




