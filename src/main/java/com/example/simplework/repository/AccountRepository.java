package com.example.simplework.repository;

import com.example.simplework.entity.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<UserAccount, Integer> {

}
