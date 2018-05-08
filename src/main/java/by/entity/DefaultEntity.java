package by.entity;

import by.Utils.annotations.GeneratedValue;
import by.Utils.annotations.GeneratedValue.GenerationType;
import by.Utils.annotations.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class DefaultEntity implements Serializable{

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;
}
