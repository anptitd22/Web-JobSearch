package com.project.webIT.services.IService;

import com.project.webIT.dtos.users.PasswordDTO;
import com.project.webIT.dtos.users.UpdateUserDTO;
import com.project.webIT.dtos.users.UserDTO;
import com.project.webIT.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;

    String login(String email, String password, Long roleId) throws Exception;

    User getUserDetailsFromToken (String extractedToken) throws Exception;

    User updateUser(Long userId, UpdateUserDTO updateUserDTO) throws Exception;

    User updatePassword(Long userId, PasswordDTO passwordDTO) throws Exception;

//    User createUserAvatar (Long userId, String url, String publicIdImages) throws Exception;

    String getPublicId(Long userId) throws Exception;
}
