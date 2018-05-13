package by.dao.jdbc.basecrud;

import by.jdbc.ConnectionPool;
import lombok.Getter;

import javax.inject.Inject;

class BaseConnectionKeeper {

    @Inject
    @Getter
    ConnectionPool connectionPool;
}
