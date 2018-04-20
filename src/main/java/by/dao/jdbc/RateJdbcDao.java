package by.dao.jdbc;

import by.dao.RateDao;
import by.entity.Rate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RateJdbcDao extends BaseJdbcDao implements RateDao {

    private void setItemParameter(ResultSet rs, Rate rate) throws SQLException {
        rate.setId(rs.getInt(1));
        rate.setRate(rs.getString(2));
    }

    private PreparedStatement selectPreparedStatement(Connection connection, int raceId) throws SQLException {
        final String SQL = "select rate.id as rateId, race_id as race.raceId, race as race.race, rate, result_id as result.resultId, result as result.result"+
                "from ((rate INNER JOIN race on rate.race_id = race.id) inner join rate_result "+
                "ON rate_result.id = rate.result_id) where race.id = ?;";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setInt(1, raceId);
        return statement;
    }

    @Override
    public List<Rate> getRates(int raceId) {
        List<Rate> rates = new ArrayList<>();
    /*    getLogger().debug("Starting reading list of rates");
        try (Connection connection = getConnectionPool().getConnection();
             PreparedStatement statement = selectPreparedStatement(connection, raceId);
             ResultSet rs = statement.executeQuery()) {
            Rate rate;
            while (rs.next()) {
                rate = new Rate();
                setItemParameter(rs, rate);
                rates.add(rate);
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }*/
        return rates;
    }

    @Override
    public Rate getRate(int rateId) {
        return null;
    }
}
