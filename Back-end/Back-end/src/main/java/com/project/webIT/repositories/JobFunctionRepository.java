package com.project.webIT.repositories;

import com.project.webIT.models.JobFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobFunctionRepository extends JpaRepository<JobFunction,Long> {
}
