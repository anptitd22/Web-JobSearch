package com.project.webIT.repositories;

import com.project.webIT.models.Company;
import com.project.webIT.models.User;
import com.project.webIT.models.UserPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
    List<UserPayment> findByUserIdOrderByUpdatedAtDesc (Long userId);

    @Query("SELECT u From UserPayment u WHERE "+
            "(:status IS NULL OR :status = '' OR u.status = :status) " +
            "AND (:keyword IS NULL OR :keyword = '' OR u.orderId LIKE %:keyword%)")
    Page<UserPayment> managerPayment
            (@Param("keyword") String keyword,
             @Param("status") String status,
             Pageable pageable);
}
