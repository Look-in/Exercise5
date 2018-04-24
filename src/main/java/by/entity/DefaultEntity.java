package by.entity;

import by.Utils.annotations.Id;
import lombok.Getter;
import lombok.Setter;

public class DefaultEntity {

    @Getter
    @Setter
    @Id
    private Integer id;
}
