package com.ozguryazilim.springmvc.repository;

import com.ozguryazilim.springmvc.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
