package by.dao;

/**
 * <code>DAO</code> For change instances<br>
 *
 * @author Serg Shankunas
 */
public interface ChangeInstance<T> {

    /**
     * Добавляет единицу сущности.
     *
     * @param entity сущность
     */
    void create(T entity);

    /**
     * Обновляет сущность.
     *
     * @param entity сущность
     */
    void update(T entity);

    /**
     * Удаляет сущность.
     *
     * @param id сущности
     */
    void delete(Integer id);
}




