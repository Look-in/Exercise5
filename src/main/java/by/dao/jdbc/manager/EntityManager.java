package by.dao.jdbc.manager;

import java.util.List;

public interface EntityManager<T> {

    T find(String sql, Class<T> tClass, Object id);

    List<T> findAll(String sql, Class<T> tClass);
}
