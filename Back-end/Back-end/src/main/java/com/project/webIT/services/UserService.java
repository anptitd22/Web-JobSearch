package com.project.webIT.services;

import com.project.webIT.components.JwtTokenUtils;
import com.project.webIT.components.LocalizationUtils;
import com.project.webIT.dtos.users.UpdateUserDTO;
import com.project.webIT.dtos.users.UserDTO;
import com.project.webIT.exception.DataNotFoundException;
import com.project.webIT.exception.PermissionDenyException;
import com.project.webIT.models.Role;
import com.project.webIT.models.User;
import com.project.webIT.repositories.RoleRepository;
import com.project.webIT.repositories.UserRepository;
import com.project.webIT.services.IService.IUserService;
import com.project.webIT.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final LocalizationUtils localizationUtils;

    @Transactional
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        //Kiem tra so dien thoai da ton tai chua
        String email = userDTO.getEmail();
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByEmail(email)){
            throw new DataIntegrityViolationException(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_EXIST_EMAIL));
        }
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_EXIST_PHONE));
        }
        Role role = roleRepository.findById(userDTO.getRoleID())
                .orElseThrow(() -> new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_ROLE)));
        if (role.getName().toUpperCase().equals(Role.ADMIN)){
            throw new PermissionDenyException("You cannot register an admin account");
        }else if(role.getName().toUpperCase().equals(Role.COMPANY)){
            throw new PermissionDenyException("You cannot register an company account");
        }
        //convert from user -> userDTO
        modelMapper.typeMap(UserDTO.class, User.class)
                .addMappings(mapper ->
                        mapper.skip(User::setId));
        User newUser = new User();
        modelMapper.map(userDTO, newUser);
        newUser.setActive(true);
        newUser.setRole(role);
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0){
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            //spring security
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Transactional
    @Override
    public String login(String email, String password, Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_EMAIL_PASS));
        }
        //security
        User existingUser = optionalUser.get();
        //check password
        if (existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0){
            if (!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadCredentialsException(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_EMAIL_PASS));
            }
        }
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getId())){
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_ROLE));
        }
        if(!optionalUser.get().isActive()){
            throw new DataNotFoundException("user is locked");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password, existingUser.getAuthorities()
        );
        //authenticate with Java spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser); //tra ve token jwt
    }

    @Transactional
    @Override
    public User getUserDetailsFromToken(String extractedToken) throws Exception {
        if(jwtTokenUtil.isTokenExpired(extractedToken)){
            throw new Exception("Token is expired");
        }
        String email = jwtTokenUtil.extractEmail(extractedToken);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            return userOptional.get();
        }else{
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
        String newPhoneNumber = updateUserDTO.getPhoneNumber();
        if(!existingUser.getPhoneNumber().equals(newPhoneNumber) && userRepository.existsByPhoneNumber(newPhoneNumber)){
            throw new DataNotFoundException("Phone number already exists");
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

        if (updateUserDTO.getFacebookAccountId() > 0) {
            existingUser.setFacebookAccountId(updateUserDTO.getFacebookAccountId());
        }

        if (updateUserDTO.getGoogleAccountId() > 0) {
            existingUser.setGoogleAccountId(updateUserDTO.getGoogleAccountId());
        }

        if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().isEmpty()) {
            if(!updateUserDTO.getPassword().equals(updateUserDTO.getRetypePassword())){
                throw new DataNotFoundException("Password and RetypePassword not The Same");
            }
            String newPassword = updateUserDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }

        return userRepository.save(existingUser);
    }
}
