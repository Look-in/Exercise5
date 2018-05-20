package by.entity;

import by.Utils.annotations.GeneratedValue;
import by.Utils.annotations.GeneratedValue.GenerationType;
import by.Utils.annotations.Id;
import lombok.Data;

import java.io.Serializable;

/**
 * Базовая сущность c Id .
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Data
class DefaultEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
