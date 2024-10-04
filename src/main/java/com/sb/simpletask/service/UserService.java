package com.sb.simpletask.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.sb.simpletask.utils.CustomResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sb.simpletask.dto.UserDto;
import com.sb.simpletask.entity.User;
import com.sb.simpletask.exception.UserAlreadyExistsException;
import com.sb.simpletask.exception.UserNotFoundException;
import com.sb.simpletask.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public CustomResponseDTO registerUser(UserDto userDto) {
        CustomResponseDTO customResponseDTO = new CustomResponseDTO();
        String username = userDto.getUsername();
        Optional<User> findByUsername = userRepository.findByUsername(username);
        if (findByUsername.isPresent()) {
            throw new UserAlreadyExistsException("User with username " + userDto.getUsername() + " already exists.");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user1 = userRepository.save(user);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", user1.getId());
        resultMap.put("username", user1.getUsername());
        customResponseDTO.setResultMap(resultMap);
        customResponseDTO.setResultType("Success");
        customResponseDTO.setMessage("User register successfully!");
        customResponseDTO.setSuccess(true);
        return customResponseDTO;
    }

    public CustomResponseDTO deleteUser(Long userId) {
        CustomResponseDTO customResponseDTO = new CustomResponseDTO();

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
        customResponseDTO.setResultType("Success");
        customResponseDTO.setMessage("User deleted successfully!");
        customResponseDTO.setSuccess(true);
        return customResponseDTO;
    }
}
