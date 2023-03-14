package com.example.simplework.service;

import com.example.simplework.constant.UserPrivilegeConstant;
import com.example.simplework.entity.model.User;
import com.example.simplework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;

@Service
public class UserAccountService {
    @Autowired
    private UserRepository userRepository;

    public User findByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public boolean isExistUser(String email) {
        return Objects.nonNull(userRepository.findByEmail(email));
    }

    private User saveOrUpdate(User user) {
        user.setUpdatedAt(Calendar.getInstance().getTime());
        return userRepository.save(user);
    }

    public User createNewUser(User user) {
        user.setUserPrivilege(UserPrivilegeConstant.CUSTOMER.getValue());
        user.setEnabled(1);
        user.setCreatedAt(Calendar.getInstance().getTime());
        return saveOrUpdate(user);
    }

    public User grantAuthority(User user) {
        if (user.getUserPrivilege() == null)
            user.getAuthorities();
        return saveOrUpdate(user);
    }
}
