package com.project.webIT.repositories;

import com.project.webIT.models.User;
import com.project.webIT.models.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
    List<UserPayment> findByUserIdOrderByUpdatedAtDesc (Long userId);
}
