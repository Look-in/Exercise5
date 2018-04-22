package by.entity;

import by.Utils.annotations.Entity;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class UserRate {

    private User user;

    private List<Rate> rates;
}
