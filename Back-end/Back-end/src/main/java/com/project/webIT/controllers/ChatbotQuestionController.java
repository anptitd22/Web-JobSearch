package com.project.webIT.controllers;

import com.project.webIT.models.ChatbotQuestion;
import com.project.webIT.services.ChatbotQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/questions")
@RequiredArgsConstructor
public class ChatbotQuestionController {
    private final ChatbotQuestionService questionService;

    @GetMapping
    public ResponseEntity<?> getAllQuestions() {
        try{
            return ResponseEntity.ok().body(questionService.getAllQuestions());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        Optional<ChatbotQuestion> question = questionService.getQuestionById(id);
        if(question.isPresent()){
            return ResponseEntity.ok().body(question.get());
        }
        return ResponseEntity.badRequest().body("not found");
    }

    @PostMapping
    public ChatbotQuestion createQuestion(@RequestBody ChatbotQuestion question) {
        return questionService.saveQuestion(question);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody ChatbotQuestion questionDetails) {
        Optional<ChatbotQuestion> questionOptional = questionService.getQuestionById(id);
        if (questionOptional.isPresent()) {
            ChatbotQuestion question = questionOptional.get();
            question.setText(questionDetails.getText());
            question.setAnswer(questionDetails.getAnswer());
            return ResponseEntity.ok(questionService.saveQuestion(question));
        } else {
            return ResponseEntity.badRequest().body("not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        Optional<ChatbotQuestion> questionOptional = questionService.getQuestionById(id);
        if (questionOptional.isPresent()) {
            questionService.deleteQuestion(id);
            return ResponseEntity.ok().body("ok");
        } else {
            return ResponseEntity.badRequest().body("not found");
        }
    }
}
