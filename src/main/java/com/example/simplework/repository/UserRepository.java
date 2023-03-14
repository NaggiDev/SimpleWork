package com.example.simplework.repository;

import com.example.simplework.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);

    User findByEmail(String email);
}
