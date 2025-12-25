package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.RoleAuthority;
import com.example.demo.model.Roles;

@Repository
public interface RoleAuthorityRepository
        extends JpaRepository<RoleAuthority, Long> {
 List<RoleAuthority> findByRole(Roles role);
    // No extra methods needed for:
    // count()
    // saveAll()
}