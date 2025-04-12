package com.project.webIT.services.IService;

import com.project.webIT.dtos.request.*;
import com.project.webIT.models.User;

public interface UserService {
    User createUser(UserDTO userDTO) throws Exception;

    String loginUser(UserLoginDTO userLoginDTO) throws Exception;

    User getUserDetailsFromToken (String extractedToken) throws Exception;

    User updateUser(Long userId, UpdateUserDTO updateUserDTO) throws Exception;

    User updatePassword(Long userId, PasswordDTO passwordDTO) throws Exception;

    String updateEmail(Long userId, EmailDTO emailDTO) throws Exception;


//    User createUserAvatar (Long userId, String url, String publicIdImages) throws Exception;

    String getPublicId(Long userId) throws Exception;

    Boolean checkSizeCV(Long userId, Long size) throws Exception;
}
