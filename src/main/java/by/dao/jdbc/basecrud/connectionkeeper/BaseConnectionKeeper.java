package by.dao.jdbc.basecrud.connectionkeeper;

import java.sql.Connection;

/**
 * Интерфейс для получение коннекта.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface BaseConnectionKeeper {

    Connection getConnection();
}
