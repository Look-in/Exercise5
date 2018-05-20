package by.service.reference;

import by.entity.RateResult;

import java.util.List;

/**
 * Интерфейс для работы со справочниками.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface ReferenceService {

    List<RateResult> getRateResults();

    RateResult getRateResult(int id);
}
