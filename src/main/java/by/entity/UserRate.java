package by.entity;

import java.util.List;

public class UserRate {

    private User user;

    private List<Rate> rates;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public void addRate(Rate rate) {
        rates.add(rate);
    }
}
