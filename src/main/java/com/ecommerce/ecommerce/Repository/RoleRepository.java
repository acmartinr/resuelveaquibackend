package com.ecommerce.ecommerce.Repository;

import java.util.Optional;

import com.ecommerce.ecommerce.Models.ERole;
import com.ecommerce.ecommerce.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}