package com.d23alex.fairbusiness.repository;

import com.d23alex.fairbusiness.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
