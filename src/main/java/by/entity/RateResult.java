package by.entity;

import by.Utils.annotations.Column;
import by.Utils.annotations.Table;
import lombok.Data;
import by.Utils.annotations.Entity;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name = "rate_result")
public class RateResult extends DefaultEntity {

    @Column(name = "result")
    private String rateResult;
}
