package com.project.webIT.services;

import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.ChatbotQuestion;
import com.project.webIT.repositories.ChatbotQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatbotQuestionServiceImpl {
    private final ChatbotQuestionRepository questionRepository;

    public List<ChatbotQuestion> getAllQuestions() {
        return questionRepository.findAll();
    }

    public ChatbotQuestion getQuestionById(Long id) throws Exception{
        Optional<ChatbotQuestion> chatbotQuestion = questionRepository.findById(id);
        if (chatbotQuestion.isEmpty()){
            throw new DataNotFoundException("not found question");
        }
        return chatbotQuestion.get();
    }

    public ChatbotQuestion saveQuestion(ChatbotQuestion question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
