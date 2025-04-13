package com.project.webIT.controllers;

import com.project.webIT.models.User;
import com.project.webIT.models.UserFavoriteCompany;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.UsersFavoriteCompaniesResponse;
import com.project.webIT.services.UserServiceImpl;
import com.project.webIT.services.UsersFavoriteCompaniesServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/my-companies")
@RequiredArgsConstructor
public class UserFavoriteCompanyController {
    private final UsersFavoriteCompaniesServiceImpl usersFavoriteCompaniesServiceImpl;
    private final UserServiceImpl userService;

    @PostMapping("{companyId}")
    public ResponseEntity<ObjectResponse<UserFavoriteCompany>> saveFavorite(
            @PathVariable("companyId") Long companyId,
            @Valid  @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        UserFavoriteCompany userFavoriteCompany =
                usersFavoriteCompaniesServiceImpl.saveFavoriteCompany(user, companyId);

        return ResponseEntity.ok(
                ObjectResponse.<UserFavoriteCompany>builder()
                        .status(HttpStatus.OK)
                        .message("Thêm/xóa danh sách công ty yêu thích")
                        .data(userFavoriteCompany)
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<ObjectResponse<List<UsersFavoriteCompaniesResponse>>> getFavorites(
            @Valid  @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception{
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        List<UserFavoriteCompany> userFavoriteCompanies = usersFavoriteCompaniesServiceImpl.getUserFavorites(user.getId());
        List<UsersFavoriteCompaniesResponse> responseList = userFavoriteCompanies.stream()
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
