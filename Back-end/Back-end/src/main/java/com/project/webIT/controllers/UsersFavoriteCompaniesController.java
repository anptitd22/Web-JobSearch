package com.project.webIT.controllers;

import com.project.webIT.models.UsersFavoriteCompanies;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.UsersFavoriteCompaniesResponse;
import com.project.webIT.services.UsersFavoriteCompaniesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/my-companies")
@RequiredArgsConstructor
public class UsersFavoriteCompaniesController {
    private final UsersFavoriteCompaniesServiceImpl usersFavoriteCompaniesServiceImpl;

    @PostMapping("{userId}/{companyId}")
    public ResponseEntity<ObjectResponse<UsersFavoriteCompanies>> saveFavorite(
            @PathVariable("userId") Long userId,
            @PathVariable("companyId") Long companyId
    ) throws Exception {
        UsersFavoriteCompanies usersFavoriteCompanies = usersFavoriteCompaniesServiceImpl.saveFavoriteCompany(userId, companyId);

        return ResponseEntity.ok(
                ObjectResponse.<UsersFavoriteCompanies>builder()
                        .status(HttpStatus.OK)
                        .message("Thêm/xóa danh sách công ty yêu thích")
                        .data(usersFavoriteCompanies)
                        .build()
        );
    }

    @GetMapping("{userId}")
    public ResponseEntity<ObjectResponse<List<UsersFavoriteCompaniesResponse>>> getFavorites(
            @PathVariable("userId") Long userId
    ) {
        List<UsersFavoriteCompanies> usersFavoriteCompanies = usersFavoriteCompaniesServiceImpl.getUserFavorites(userId);
        List<UsersFavoriteCompaniesResponse> responseList = usersFavoriteCompanies.stream()
                .map(UsersFavoriteCompaniesResponse::fromUserFavoriteCompanies)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                ObjectResponse.<List<UsersFavoriteCompaniesResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("Lấy danh sách công ty yêu thích")
                        .data(responseList)
                        .build()
        );
    }
}
