package by.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class User extends Entity{

    private String userName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String password;
}
