package by.dao.jdbc.basecrud.connectionkeeper;

import java.sql.Connection;

public interface BaseConnectionKeeper {

    Connection getConnection();
}
