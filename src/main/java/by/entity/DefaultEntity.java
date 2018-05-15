package by.entity;

import by.Utils.annotations.GeneratedValue;
import by.Utils.annotations.GeneratedValue.GenerationType;
import by.Utils.annotations.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
class DefaultEntity implements Serializable{

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;
}
