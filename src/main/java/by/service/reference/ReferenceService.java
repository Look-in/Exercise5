package by.service.reference;

import by.entity.RateResult;

import java.util.List;

public interface ReferenceService {

    List<RateResult> getRateResults();

    RateResult getRateResult(int id);
}
