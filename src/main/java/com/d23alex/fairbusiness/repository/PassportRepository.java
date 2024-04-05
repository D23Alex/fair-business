package com.d23alex.fairbusiness.repository;

import com.d23alex.fairbusiness.model.Passport;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PassportRepository extends CrudRepository<Passport, Long> {
    Optional<Passport> findByNumberAndSeries(Long number, Long series);

    Optional<Passport> findTopByNumberAndSeriesOrderByGiven(Long number, Long series);
}
