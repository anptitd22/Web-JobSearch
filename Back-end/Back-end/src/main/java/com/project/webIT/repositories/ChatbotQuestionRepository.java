package com.project.webIT.repositories;

import com.project.webIT.models.ChatbotQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatbotQuestionRepository extends JpaRepository<ChatbotQuestion, Long> {
}
