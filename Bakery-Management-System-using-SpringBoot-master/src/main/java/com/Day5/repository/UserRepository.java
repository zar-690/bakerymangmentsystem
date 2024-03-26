package com.Day5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Day5.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findUserByEmail(String email);
}
