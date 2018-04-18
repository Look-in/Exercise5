package by.entity;

public class Rate {

    private int rateId;

    private String rate;

    private Race race;

    private RateResult rateResult;

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public RateResult getRateResult() {
        return rateResult;
    }

    public void setRateResult(RateResult rateResult) {
        this.rateResult = rateResult;
    }
}
