package com.d23alex.fairbusiness.repository;

import com.d23alex.fairbusiness.model.BankBan;
import com.d23alex.fairbusiness.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BankBanRepository extends CrudRepository<BankBan, Long> {
    List<BankBan> findAllByPerson(Person person);
}
