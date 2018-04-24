package by.dao;

import by.entity.Race;

import java.util.List;

public interface RaceDao extends dao.ChangeInstance<Race> {

    List<Race> getRaces();

    Race getRace(int raceId);
}
