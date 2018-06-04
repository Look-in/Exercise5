package by.entity;

import by.Utils.annotations.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Сущность забег.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Race extends DefaultEntity {

    private String race;
}
