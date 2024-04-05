package com.d23alex.fairbusiness.repository;

import com.d23alex.fairbusiness.model.AcquiredTariff;
import com.d23alex.fairbusiness.model.IndividualCheck;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IndividualCheckRepository extends CrudRepository<IndividualCheck, Long> {
    public List<IndividualCheck> findAllByAcquiredTariff(AcquiredTariff acquiredTariff);
}
