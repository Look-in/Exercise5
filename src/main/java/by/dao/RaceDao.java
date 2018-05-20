package by.dao;

import by.entity.Race;

import java.util.List;

/**
 * Интерфейс для работы с Race.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface RaceDao extends ChangeInstance<Race> {

    List<Race> getRaces();

    Race getRace(int raceId);
}
