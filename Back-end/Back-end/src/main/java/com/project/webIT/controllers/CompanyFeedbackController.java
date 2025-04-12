package com.project.webIT.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/feedback")
public class CompanyFeedbackController {
//    private final CompanyFeedbackServiceImpl companyFeedbackServiceImpl;
//
//    @PostMapping("")
//    public ResponseEntity<?> createCompanyFeedback(
//            @Valid @RequestBody CompanyFeedbackDTO companyFeedbackDTO,
//            BindingResult result
//    ){
//        try {
//            if(result.hasErrors()){
//                List<String> errorMessages = result.getFieldErrors()
//                        .stream()
//                        .map(FieldError::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.badRequest().body(errorMessages);
//            }
//            CompanyFeedback companyFeedback = companyFeedbackServiceImpl.createCompanyFeedback(companyFeedbackDTO);
//            return ResponseEntity.ok().body(companyFeedback);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @GetMapping("company/{company_id}") //danh sach apply
//    public ResponseEntity<?> getCompanyFeedbacks(@Valid @PathVariable("company_id") Long companyId){
//        try {
//            List<CompanyFeedback> companyFeedbacks = companyFeedbackServiceImpl.findByCompanyId(companyId);
//            return ResponseEntity.ok().body(companyFeedbacks);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getCompanyFeedback(@Valid @PathVariable("id") Long id){
//        try {
//            CompanyFeedback existingCompanyFeedback = companyFeedbackServiceImpl.getCompanyFeedback(id);
//            return ResponseEntity.ok().body(existingCompanyFeedback);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    @Transactional
//    //xoa cung
//    public ResponseEntity<String> deleteCompanyFeedback(@Valid @PathVariable Long id){
//        companyFeedbackServiceImpl.deleteCompanyFeedback(id);
//        return ResponseEntity.ok().body("delete company response successfully with id = "+id);
//    }
}
