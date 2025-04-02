package com.project.webIT.repositories;

import com.project.webIT.models.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
}
