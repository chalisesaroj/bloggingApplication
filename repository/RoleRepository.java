package com.udemy.learn.blogging.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.learn.blogging.entity.Role;
import com.udemy.learn.blogging.entity.User;

public interface RoleRepository extends JpaRepository<Role, Long> {
Optional<Role>findByName(String role);
boolean existsByName(String role);
}
