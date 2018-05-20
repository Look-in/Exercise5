package by.entity;

import by.Utils.annotations.Entity;
import by.Utils.annotations.JoinColumn;
import by.Utils.annotations.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Сущность ставка.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Rate extends DefaultEntity {

    private String rate;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne
    @JoinColumn(name = "result_id")
    private RateResult rateResult;


}
