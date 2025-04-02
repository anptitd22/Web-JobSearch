package com.project.webIT.repositories;

import com.project.webIT.models.User;
import com.project.webIT.models.UserCV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCvRepository extends JpaRepository<UserCV, Long> {
    List<UserCV> findByUserIdAndIsActiveTrueOrderByUpdatedAtDesc(Long userId);
}
