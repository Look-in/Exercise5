package by.entity;

import by.Utils.annotations.Entity;
import by.Utils.annotations.JoinColumn;
import by.Utils.annotations.JoinTable;
import by.Utils.annotations.OneToMany.FetchType;
import by.Utils.annotations.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class UserRate {

    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rates",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rate_id"))
    private List<Rate> rates;
}
