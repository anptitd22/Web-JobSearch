package com.project.webIT.repositories;

import com.project.webIT.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
    //SELECT * FROM user WHERE email = ?
    Optional<User> findByEmail(String email);

    Optional<User> findByFacebookAccountId (String facebookAccountId);

    Optional<User> findByGoogleAccountId (String googleAccountId);
}
