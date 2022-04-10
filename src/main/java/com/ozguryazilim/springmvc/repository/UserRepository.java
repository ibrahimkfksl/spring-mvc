package com.ozguryazilim.springmvc.repository;

import com.ozguryazilim.springmvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
