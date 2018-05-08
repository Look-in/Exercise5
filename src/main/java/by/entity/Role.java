package by.entity;

import by.Utils.annotations.Entity;
import by.Utils.annotations.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name = "Roles")
public class Role extends DefaultEntity {

    private String role;
}
