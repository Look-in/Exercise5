package by.entity;

import by.Utils.annotations.*;
import by.Utils.annotations.OneToMany.FetchType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name = "users")
public class User extends DefaultEntity {

    private String userName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_rates",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rate_id"))
    private Set<Rate> rates;
}
