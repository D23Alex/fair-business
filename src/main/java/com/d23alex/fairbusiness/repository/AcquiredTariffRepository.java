package com.d23alex.fairbusiness.repository;

import com.d23alex.fairbusiness.model.AcquiredTariff;
import com.d23alex.fairbusiness.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AcquiredTariffRepository extends CrudRepository<AcquiredTariff, Long> {
    List<AcquiredTariff> findAllByUser(User user);
}
