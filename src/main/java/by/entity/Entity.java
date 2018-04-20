package by.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

public class Entity implements Serializable{

    @Getter
    @Setter
    private int id;
}
