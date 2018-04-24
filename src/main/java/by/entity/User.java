package by.entity;

import by.Utils.annotations.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
public class User extends DefaultEntity {

    private String userName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String password;

    @OneToMany(fetch = OneToMany.FetchType.LAZY)
    @JoinTable(name = "user_rates",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rate_id"))
    private List<Rate> rates;
}
