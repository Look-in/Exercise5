package by.entity;

import lombok.Data;

@Data
public class Rate extends Entity{

    private String rate;

    private Race race;

    private RateResult rateResult;
}
