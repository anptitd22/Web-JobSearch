package com.project.webIT.services;

import com.project.webIT.models.ChatbotQuestion;
import com.project.webIT.repositories.ChatbotQuestionRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatbotQuestionService {
    private final ChatbotQuestionRepository questionRepository;

    public List<ChatbotQuestion> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<ChatbotQuestion> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public ChatbotQuestion saveQuestion(ChatbotQuestion question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
