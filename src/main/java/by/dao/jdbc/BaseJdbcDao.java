package by.dao.jdbc;

import by.dao.jdbc.manager.EntityManager;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

class BaseJdbcDao {

    @Inject
    @Getter
    private EntityManager entityManager;

    @Getter
    private Logger logger = LoggerFactory.getLogger(getClass());
}
