package by.service;

import by.entity.Race;

import java.util.List;

/**
 * Интерфейс для работы с забегами.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface RaceService {

    List<Race> getRaces();

    Race getRace(int raceId);

    void pushRace(Race race);

    void deleteRace(int raceId);
}
