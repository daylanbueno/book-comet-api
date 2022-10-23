package com.bookcomet.service.impl;

import com.bookcomet.config.JwtService;
import com.bookcomet.converters.UserConverter;
import com.bookcomet.dto.DtoUser;
import com.bookcomet.entity.User;
import com.bookcomet.exceptions.BusinessException;
import com.bookcomet.exceptions.InvalidLoginOrPassword;
import com.bookcomet.repository.UserRepository;
import com.bookcomet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final UserConverter userConverter;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public DtoUser save(DtoUser dtoUser) {
        User user = userConverter.converterToEntity(dtoUser);

        if(userRepository.existsByEmail(dtoUser.getEmail())) {
            throw new BusinessException("The user is already registered");
        }

        user.setPassword(passwordEncoder.encode(dtoUser.getPassword()));

        User newUser = userRepository.save(user);
        return userConverter.converterToDto(newUser);
    }

    @Override
    public String login(DtoUser auth) {

        User user = userRepository.findByEmail(auth.getEmail()).
                orElseThrow(InvalidLoginOrPassword::new);
        ValidatePassword(auth, user);

        return jwtService.gerarToken(user);
    }

    private void ValidatePassword(DtoUser auth, User user) {
        boolean isPasswordValid = passwordEncoder.matches(auth.getPassword(), user.getPassword());
        if (!isPasswordValid) {
            throw new InvalidLoginOrPassword();
        }
    }
}
