package com.project.webIT.services;

import com.project.webIT.helper.JwtTokenHelper;
import com.project.webIT.components.LocalizationUtils;
import com.project.webIT.dtos.request.*;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.exceptions.InvalidParamException;
import com.project.webIT.models.Role;
import com.project.webIT.models.User;
import com.project.webIT.repositories.RoleRepository;
import com.project.webIT.repositories.UserRepository;
import com.project.webIT.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements com.project.webIT.services.IService.UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenHelper jwtTokenHelper;
    private final AuthenticationManager authenticationManager;
    private final LocalizationUtils localizationUtils;

    @Transactional
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        if(userDTO.getRoleID()!=1){
            throw new DataNotFoundException("Can only create account user");
        }

        Role role = roleRepository.findById((long)1)
                .orElseThrow(()-> new DataNotFoundException("error role user"));

        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())){
            throw new InvalidParamException(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH));
        }
        //Kiem tra so dien thoai da ton tai chua
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new DataIntegrityViolationException(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_EXIST_EMAIL));
        }

        if(!userDTO.getPhoneNumber().isEmpty()&&userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())){
            throw new DataIntegrityViolationException(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_EXIST_PHONE));
        }
        //convert from user -> userDTO
        modelMapper.typeMap(UserDTO.class, User.class)
                .addMappings(mapper ->
                        mapper.skip(User::setId));
        User newUser = new User();
        modelMapper.map(userDTO, newUser);
        newUser.setActive(true);
        newUser.setRole(role);
        if (userDTO.getFacebookAccountId().isEmpty() && userDTO.getGoogleAccountId().isEmpty()){
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            //spring security
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Transactional
    @Override
    public String loginUser(UserLoginDTO userLoginDTO) throws Exception {
        // 1. Kiểm tra role tồn tại
        Optional<Role> optionalRole = roleRepository.findById(userLoginDTO.getRoleId());
        if (optionalRole.isEmpty()) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_ROLE));
        }
        Role role = optionalRole.get();

        // 2. Xử lý đăng nhập theo nhiều phương thức

        // 2.1 Kiểm tra đăng nhập qua Facebook
        if (!StringUtils.isEmpty(userLoginDTO.getFacebookAccountId())) {
            return handleFacebookLogin(userLoginDTO, role);
        }

        // 2.2 Kiểm tra đăng nhập qua Google
        if (!StringUtils.isEmpty(userLoginDTO.getGoogleAccountId())) {
            return handleGoogleLogin(userLoginDTO, role);
        }

        // 2.3 Xử lý đăng nhập thông thường
        return handleNormalLogin(userLoginDTO, role);
    }

    // Xử lý đăng nhập Facebook
    private String handleFacebookLogin(UserLoginDTO userLoginDTO, Role role) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(userLoginDTO.getEmail());

        // Nếu email không tồn tại, tìm theo Facebook ID
        if (optionalUser.isEmpty()) {
            optionalUser = userRepository.findByFacebookAccountId(userLoginDTO.getFacebookAccountId());

            // Nếu chưa có tài khoản, tạo mới
            if (optionalUser.isEmpty()) {
                // Kiểm tra số điện thoại đã tồn tại chưa
                if (userLoginDTO.getPhoneNumber() != null
                        && !userLoginDTO.getPhoneNumber().isBlank()
                        && userRepository.existsByPhoneNumber(userLoginDTO.getPhoneNumber())) {
                    throw new DataNotFoundException("Phone number already exists");
                }

                // Tạo user mới
                User newUser = User.builder()
                        .isActive(true)
                        .fullName(userLoginDTO.getFullName() != null ? userLoginDTO.getFullName() : "")
                        .email(userLoginDTO.getEmail())
                        .avatar(userLoginDTO.getAvatar())
                        .facebookAccountId(userLoginDTO.getFacebookAccountId())
                        .role(role)
                        .password("")
                        .build();

                userRepository.save(newUser);
                return jwtTokenHelper.generateTokenFromUser(newUser);
            }
        }

        // Tài khoản tồn tại, kiểm tra trạng thái và quyền
        User existingUser = optionalUser.get();

        // Liên kết Facebook ID nếu chưa liên kết
        if (StringUtils.isEmpty(existingUser.getFacebookAccountId())) {
            existingUser.setFacebookAccountId(userLoginDTO.getFacebookAccountId());
            userRepository.save(existingUser);
        }

        validateUserStatus(existingUser);
        validateUserRole(existingUser, role);

        return jwtTokenHelper.generateTokenFromUser(existingUser);
    }

    // Xử lý đăng nhập Google
    private String handleGoogleLogin(UserLoginDTO userLoginDTO, Role role) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(userLoginDTO.getEmail());

        // Nếu email không tồn tại, tìm theo Google ID
        if (optionalUser.isEmpty()) {
            optionalUser = userRepository.findByGoogleAccountId(userLoginDTO.getGoogleAccountId());

            // Nếu chưa có tài khoản, tạo mới
            if (optionalUser.isEmpty()) {
                // Kiểm tra số điện thoại đã tồn tại chưa
                if (userLoginDTO.getPhoneNumber() != null
                        && !userLoginDTO.getPhoneNumber().isBlank()
                        && userRepository.existsByPhoneNumber(userLoginDTO.getPhoneNumber())) {
                    throw new DataNotFoundException("Phone number already exists");
                }

                // Tạo user mới
                User newUser = User.builder()
                        .isActive(true)
                        .fullName(userLoginDTO.getFullName() != null ? userLoginDTO.getFullName() : "")
                        .email(userLoginDTO.getEmail())
                        .avatar(userLoginDTO.getAvatar())
                        .googleAccountId(userLoginDTO.getGoogleAccountId())
                        .role(role)
                        .password("")
                        .build();

                userRepository.save(newUser);
                return jwtTokenHelper.generateTokenFromUser(newUser);
            }
        }

        // Tài khoản tồn tại, kiểm tra trạng thái và quyền
        User existingUser = optionalUser.get();

        // Liên kết Google ID nếu chưa liên kết
        if (StringUtils.isEmpty(existingUser.getGoogleAccountId())) {
            existingUser.setGoogleAccountId(userLoginDTO.getGoogleAccountId());
            userRepository.save(existingUser);
        }

        validateUserStatus(existingUser);
        validateUserRole(existingUser, role);

        return jwtTokenHelper.generateTokenFromUser(existingUser);
    }

    // Xử lý đăng nhập thông thường
    private String handleNormalLogin(UserLoginDTO userLoginDTO, Role role) throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getEmail(),
                        userLoginDTO.getPassword()
                )
        );
        User existingUser = (User) authentication.getPrincipal();

        // Nếu tài khoản đã đăng ký bằng mạng xã hội, thông báo đăng nhập bằng mạng xã hội
        if (!existingUser.getGoogleAccountId().isEmpty() ||
                !existingUser.getFacebookAccountId().isEmpty()) {
            throw new BadCredentialsException("Please login with your social account");
        }

        validateUserStatus(existingUser);
        validateUserRole(existingUser, role);

        return jwtTokenHelper.generateTokenFromUser(existingUser);
    }

    private void validateUserStatus(User user) throws DataNotFoundException {
        if (!user.isActive()) {
            throw new DataNotFoundException("User is locked");
        }
    }

    // Kiểm tra role của người dùng
    private void validateUserRole(User user, Role requestedRole) throws DataNotFoundException {
        if (requestedRole.getId() != 1 || !requestedRole.getId().equals(user.getRole().getId())) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_ROLE));
        }
    }

    @Transactional
    @Override
    public User getUserDetailsFromToken(String extractedToken) throws Exception {
        if(jwtTokenHelper.isTokenExpired(extractedToken)){
            throw new Exception("ForgotToken is expired");
        }
        String email = jwtTokenHelper.extractSubject(extractedToken);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            return userOptional.get();
        } else {
            throw new Exception("User not found");
        }
    }

    @Transactional
    @Override
    public User updateUser(Long userId, UpdateUserDTO updateUserDTO) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found !"));
        String newEmail = updateUserDTO.getEmail();
        if(!existingUser.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)){
            throw new DataNotFoundException("Email already exists");
        }
        if(existingUser.getPhoneNumber() != null && !existingUser.getPhoneNumber().isEmpty()){
            String newPhoneNumber = updateUserDTO.getPhoneNumber();
            if(!existingUser.getPhoneNumber().equals(newPhoneNumber) && userRepository.existsByPhoneNumber(newPhoneNumber)){
                throw new DataNotFoundException("Phone number already exists");
            }
        }

        // Assuming 'updateUserDTO' is already populated with the new user data

        if (updateUserDTO.getFirstName() != null && !updateUserDTO.getFirstName().isEmpty()) {
            existingUser.setFirstName(updateUserDTO.getFirstName());
        }

        if (updateUserDTO.getLastName() != null && !updateUserDTO.getLastName().isEmpty()) {
            existingUser.setLastName(updateUserDTO.getLastName());
        }

        if (updateUserDTO.getFullName() != null && !updateUserDTO.getFullName().isEmpty()) {
            existingUser.setFullName(updateUserDTO.getFullName());
        }

        if (updateUserDTO.getPhoneNumber() != null && !updateUserDTO.getPhoneNumber().isEmpty()) {
            if(!updateUserDTO.getPhoneNumber().equals(existingUser.getPhoneNumber())){
                if(userRepository.existsByPhoneNumber(updateUserDTO.getPhoneNumber())){
                    throw new DataIntegrityViolationException(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_EXIST_PHONE));
                }
            }
            existingUser.setPhoneNumber(updateUserDTO.getPhoneNumber());
        }

        if (updateUserDTO.getAddress() != null && !updateUserDTO.getAddress().isEmpty()) {
            existingUser.setAddress(updateUserDTO.getAddress());
        }

        if (updateUserDTO.getDateOfBirth() != null) { // No need to check for empty since it's a Date object
            existingUser.setDateOfBirth(updateUserDTO.getDateOfBirth());
        }

        if (updateUserDTO.getCurrentIndustry() != null && !updateUserDTO.getCurrentIndustry().isEmpty()) {
            existingUser.setCurrentIndustry(updateUserDTO.getCurrentIndustry());
        }
        if (updateUserDTO.getCurrentJobFunction() != null && !updateUserDTO.getCurrentJobFunction().isEmpty()) {
            existingUser.setCurrentJobFunction(updateUserDTO.getCurrentJobFunction());
        }
        if (updateUserDTO.getCurrentJobLevel() != null && !updateUserDTO.getCurrentJobLevel().isEmpty()) {
            existingUser.setCurrentJobLevel(updateUserDTO.getCurrentJobLevel());
        }
        if (updateUserDTO.getHighestEducation() != null && !updateUserDTO.getHighestEducation().isEmpty()) {
            existingUser.setHighestEducation(updateUserDTO.getHighestEducation());
        }
        if (updateUserDTO.getNationality() != null && !updateUserDTO.getNationality().isEmpty()) {
            existingUser.setNationality(updateUserDTO.getNationality());
        }
        if (updateUserDTO.getYearsOfExperience() != null && updateUserDTO.getYearsOfExperience()>0) {
            existingUser.setYearsOfExperience(updateUserDTO.getYearsOfExperience());
        }
        if (updateUserDTO.getNote() != null && !updateUserDTO.getNote().isEmpty()) {
            existingUser.setNote(updateUserDTO.getNote());
        }
        if (updateUserDTO.getJobTitle() != null && !updateUserDTO.getJobTitle().isEmpty()) {
            existingUser.setJobTitle(updateUserDTO.getJobTitle());
        }
        if (updateUserDTO.getGender() != null && !updateUserDTO.getGender().isEmpty()) {
            existingUser.setGender(updateUserDTO.getGender());
        }
        if (updateUserDTO.getMaritalStatus() != null && !updateUserDTO.getMaritalStatus().isEmpty()) {
            existingUser.setMaritalStatus(updateUserDTO.getMaritalStatus());
        }

        if (updateUserDTO.getAvatar() != null && !updateUserDTO.getAvatar().isEmpty()) {
            existingUser.setAvatar(updateUserDTO.getAvatar());
        }

        if (updateUserDTO.getPublicIdImages() != null && !updateUserDTO.getPublicIdImages().isEmpty()) {
            existingUser.setPublicIdImages(updateUserDTO.getPublicIdImages());
        }

        if (updateUserDTO.getFacebookAccountId() != null && !updateUserDTO.getFacebookAccountId().isEmpty()) {
            existingUser.setFacebookAccountId(updateUserDTO.getFacebookAccountId());
        }

        if (updateUserDTO.getGoogleAccountId() != null && !updateUserDTO.getGoogleAccountId().isEmpty()) {
            existingUser.setGoogleAccountId(updateUserDTO.getGoogleAccountId());
        }

        if (updateUserDTO.getPassword() != null
                && !updateUserDTO.getPassword().isEmpty()
                && existingUser.getFacebookAccountId().isEmpty()
                && existingUser.getGoogleAccountId().isEmpty()) {
            if(!updateUserDTO.getPassword().equals(updateUserDTO.getRetypePassword())){
                throw new DataNotFoundException("Password and RetypePassword not The Same");
            }
            String newPassword = updateUserDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }

        return userRepository.save(existingUser);
    }

    @Override
    public User updatePassword(User existingUser, PasswordDTO passwordDTO) throws Exception {
        if(!existingUser.getGoogleAccountId().isEmpty() || !existingUser.getFacebookAccountId().isEmpty()){
            throw new DataNotFoundException("Cannot update mail in account facebook or google");
        }
        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), existingUser.getPassword())){
            throw new DataNotFoundException("Password and current password not same");
        }
        if (passwordDTO.getPassword() != null && !passwordDTO.getPassword().isEmpty()) {
            if(!passwordDTO.getPassword().equals(passwordDTO.getRetypePassword())){
                throw new DataNotFoundException("Password and RetypePassword not The Same");
            }
            String newPassword = passwordDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }
        return userRepository.save(existingUser);
    }

    @Override
    public String updateEmail(Long userId, EmailDTO emailDTO) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found !"));
        if(!existingUser.getGoogleAccountId().isEmpty() || !existingUser.getFacebookAccountId().isEmpty()){
            throw new DataNotFoundException("Cannot update mail in account facebook or google");
        }
        if(userRepository.existsByEmail(emailDTO.getNewEmail())){
            throw new DataIntegrityViolationException(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_EXIST_EMAIL));
        }
        if (!passwordEncoder.matches(emailDTO.getCurrentPassword(), existingUser.getPassword())){
            throw new DataNotFoundException("Password and current password not same");
        }
        if (emailDTO.getNewEmail() != null && !emailDTO.getNewEmail().isEmpty()) {
            existingUser.setEmail(emailDTO.getNewEmail());
        }
        userRepository.save(existingUser);
        return jwtTokenHelper.generateTokenFromUser(existingUser);
    }

    @Override
    public String getPublicId(Long userId) throws Exception{
        User existingUser = userRepository.findById(userId)
                .orElseThrow(()->new DataNotFoundException("user not found"));
        if(existingUser.getPublicIdImages() != null && !existingUser.getPublicIdImages().isEmpty()){
            return existingUser.getPublicIdImages();
        }
        return "";
    }

    @Override
    public Boolean checkSizeCV(Long userId, Long size) throws Exception{
        User existingUser = userRepository.findById(userId)
                .orElseThrow(()->new DataNotFoundException("user not found"));
        if(existingUser.getUserCVs().size()==5){
            return false;
        }
        return (existingUser.getUserCVs().size()+size) <= 5;
    }
//    @Override
//    public User createUserAvatar(Long userId, String url, String publicIdImages) throws Exception{
//        User existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new DataNotFoundException("User not found !"));
//        existingUser.setAvatar(url);
//        existingUser.setPublicIdImages(publicIdImages);
//        return userRepository.save(existingUser);
//    }
}
