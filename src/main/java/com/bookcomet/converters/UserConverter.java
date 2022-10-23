package com.bookcomet.converters;

import com.bookcomet.dto.DtoUser;
import com.bookcomet.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final ModelMapper modelMapper;

    public DtoUser converterToDto(User user) {
        return modelMapper.map(user, DtoUser.class);
    }

    public User converterToEntity(DtoUser dtoUser) {
        return modelMapper.map(dtoUser, User.class);
    }
}
