package by.service;

import by.entity.Rate;
import by.entity.User;

public interface UserRateService {

    void placeUserRate(User user, Rate rate);

    void replaceUserRate(User user, Rate rate);
}
