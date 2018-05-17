package by.entity;

import by.Utils.annotations.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Race extends DefaultEntity {

    private String race;
}
