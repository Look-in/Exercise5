package by.entity;

import lombok.Data;

@Data
public class Role extends Entity{

    private String role;

    public static final String SQL = "SELECT * FROM Roles WHERE id=?;";
}
