package com.kamneklogs.fullapp.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kamneklogs.fullapp.security.entity.Role;
import com.kamneklogs.fullapp.security.enums.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleName roleName);
}
