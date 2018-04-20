package by.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserRate {

    private User user;

    private List<Rate> rates;
}
