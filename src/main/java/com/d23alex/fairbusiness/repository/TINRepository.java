package com.d23alex.fairbusiness.repository;

import com.d23alex.fairbusiness.model.TIN;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TINRepository extends CrudRepository<TIN, Long> {
    Optional<TIN> findByTIN(Long TIN);
}
