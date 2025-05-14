package com.project.webIT.repositories;

import com.project.webIT.models.User;
import com.project.webIT.models.UserPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
    //SELECT * FROM user WHERE email = ?
    Optional<User> findByEmail(String email);

    Optional<User> findByFacebookAccountId (String facebookAccountId);

    Optional<User> findByGoogleAccountId (String googleAccountId);

    @Query("SELECT u From User u WHERE "+
            "(:active IS NULL OR u.isActive = :active) " +
            "AND (:keyword IS NULL OR :keyword = '' OR u.fullName LIKE %:keyword%)")
    Page<User> managerUser
            (@Param("keyword") String keyword,
             @Param("active") Boolean active,
             Pageable pageable);
}
