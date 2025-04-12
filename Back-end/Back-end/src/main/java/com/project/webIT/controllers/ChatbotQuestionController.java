package com.project.webIT.controllers;

import com.project.webIT.models.ChatbotQuestion;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.services.ChatbotQuestionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/questions")
@RequiredArgsConstructor
public class ChatbotQuestionController {
    private final ChatbotQuestionServiceImpl questionServiceImpl;

    @GetMapping
    public ResponseEntity<ObjectResponse<List<ChatbotQuestion>>> getAllQuestions() {
        List<ChatbotQuestion> questions = questionServiceImpl.getAllQuestions();
        ObjectResponse<List<ChatbotQuestion>> response = ObjectResponse.<List<ChatbotQuestion>>builder()
                .message("Bạn đã tải thành công câu hỏi chatbot")
                .status(HttpStatus.OK)
                .data(questions)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectResponse<ChatbotQuestion>> getQuestionById(@PathVariable Long id) throws Exception {
        ChatbotQuestion question = questionServiceImpl.getQuestionById(id);
        ObjectResponse<ChatbotQuestion> response = ObjectResponse.<ChatbotQuestion>builder()
                .message("Bạn đã chọn câu hỏi " + id)
                .status(HttpStatus.OK)
                .data(question)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ObjectResponse<ChatbotQuestion>> createQuestion(@RequestBody ChatbotQuestion question) {
        ChatbotQuestion saved = questionServiceImpl.saveQuestion(question);
        ObjectResponse<ChatbotQuestion> response = ObjectResponse.<ChatbotQuestion>builder()
                .message("Bạn đã thêm câu hỏi thành công")
                .status(HttpStatus.OK)
                .data(saved)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectResponse<ChatbotQuestion>> updateQuestion(
            @PathVariable Long id,
            @RequestBody ChatbotQuestion questionDetails
    ) throws Exception {
        ChatbotQuestion question = questionServiceImpl.getQuestionById(id);
        question.setText(questionDetails.getText());
        question.setAnswer(questionDetails.getAnswer());
        ChatbotQuestion updated = questionServiceImpl.saveQuestion(question);

        ObjectResponse<ChatbotQuestion> response = ObjectResponse.<ChatbotQuestion>builder()
                .message("Bạn đã đổi câu hỏi")
                .status(HttpStatus.OK)
                .data(updated)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectResponse<Void>> deleteQuestion(@PathVariable Long id) throws Exception {
        questionServiceImpl.getQuestionById(id); // Kiểm tra tồn tại
        questionServiceImpl.deleteQuestion(id);

        ObjectResponse<Void> response = ObjectResponse.<Void>builder()
                .message("Bạn đã xóa câu hỏi " + id)
                .status(HttpStatus.OK)
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }
}
