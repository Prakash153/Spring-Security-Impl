package com.Prakash.springSecurityApplication.services;

import com.Prakash.springSecurityApplication.dtos.SignUpDto;
import com.Prakash.springSecurityApplication.dtos.UserDto;
import com.Prakash.springSecurityApplication.entities.User;
import com.Prakash.springSecurityApplication.exceptions.ResourceNotFoundException;
import com.Prakash.springSecurityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("user not found with email: " + username));
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user not found with userId: " + userId));
    }

    public UserDto signUp(SignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());

        if (user.isPresent()) {
            throw new BadCredentialsException("User already Exists with email " + signUpDto.getEmail());
        }

        User toBeCreated = modelMapper.map(signUpDto, User.class);
        toBeCreated.setPassword(passwordEncoder.encode(toBeCreated.getPassword()));
        User savedUser = userRepository.save(toBeCreated);

        return modelMapper.map(savedUser, UserDto.class);
    }
}
