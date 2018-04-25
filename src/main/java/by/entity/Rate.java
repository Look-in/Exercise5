package by.entity;

import by.Utils.annotations.JoinColumn;
import by.Utils.annotations.ManyToOne;
import by.Utils.annotations.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
public class Rate extends DefaultEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3888143097328446772L;

	private String rate;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne
    @JoinColumn(name = "result_id")
    private RateResult rateResult;


}
