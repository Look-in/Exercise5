package by.dao.jdbc;


import by.dao.RaceDao;
import by.entity.Race;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RaceJdbcDao extends BaseJdbcDao implements RaceDao {

    private void setItemParameter(ResultSet rs, Race race) throws SQLException {
        race.setRaceId(rs.getInt(1));
        race.setName(rs.getString(2));
    }

    @Override
    public List<Race> getRaces() {
        final String SQL = "SELECT ID,RACE FROM RACE;";
        List<Race> races = new ArrayList<>();
        getLogger().debug("Starting reading List races");
        try (Connection connection = getConnectionPool().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQL)) {
            Race race;
            while (rs.next()) {
                race = new Race();
                setItemParameter(rs, race);
                races.add(race);
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
        return races;
    }

    @Override
    public Race getRace(int raceId) {
        return null;
    }
}
