package by.service;

import by.entity.Race;

import java.util.List;

public interface RaceService {

    List<Race> getRaces();

    Race getRace(int raceId);

    void pushRace(Race race);
}
