package by.entity;

import by.Utils.annotations.Entity;
import by.Utils.annotations.JoinColumn;
import by.Utils.annotations.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
public class User extends DefaultEntity {

    private String userName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String password;
}
