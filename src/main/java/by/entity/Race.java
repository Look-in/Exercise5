package by.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
public class Race extends Entity{

    @Column(name = "race")
    private String race;
}
