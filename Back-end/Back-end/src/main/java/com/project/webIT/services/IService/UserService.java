package com.project.webIT.services.IService;

import com.project.webIT.dtos.request.*;
import com.project.webIT.dtos.response.UserPaymentResponse;
import com.project.webIT.dtos.response.UserResponse;
import com.project.webIT.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    User createUser(UserDTO userDTO) throws Exception;

    String loginUser(UserLoginDTO userLoginDTO) throws Exception;

    User getUserDetailsFromToken (String extractedToken) throws Exception;

    User updateUser(Long userId, UpdateUserDTO updateUserDTO) throws Exception;

    User updatePassword(User existingUser, PasswordDTO passwordDTO) throws Exception;

    String updateEmail(Long userId, EmailDTO emailDTO) throws Exception;

    Page<UserResponse> managerUser (String keyword, Boolean status, PageRequest pageRequest);
//    User createUserAvatar (Long userId, String url, String publicIdImages) throws Exception;

    String getPublicId(Long userId) throws Exception;

    void deleteUser(Long id);

    Boolean checkSizeCV(Long userId, Long size) throws Exception;
}
