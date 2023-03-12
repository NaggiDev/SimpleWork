package com.example.simplework.entity.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "USER_NAME", nullable = false, length = 10)
    private String userName;

    @Column(name = "PASSWORD", length = 16)
    private String password;

    @Column(name = "USER_PRIVILEGE")
    private Integer userPrivilege;

    @Column(name = "ACCOUNT_STATUS", length = 11)
    private Integer accountStatus;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

}