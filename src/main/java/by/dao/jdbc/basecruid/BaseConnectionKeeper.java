package by.dao.jdbc.basecruid;

import by.jdbc.ConnectionPool;
import lombok.Getter;

import javax.inject.Inject;

class BaseConnectionKeeper {

    @Inject
    @Getter
    ConnectionPool connectionPool;
}
