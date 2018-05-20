package by.entity;

import by.Utils.annotations.Column;
import by.Utils.annotations.Entity;
import by.Utils.annotations.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Сущность результат ставки.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "rate_result")
public class RateResult extends DefaultEntity {

    @Column(name = "result")
    private String rateResult;
}
