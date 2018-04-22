package by.entity;

import lombok.Data;
import by.Utils.annotations.Entity;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
public class Race extends DefaultEntity {

    private String race;
}
