package com.example.backend.models.mappers;

import com.example.backend.models.User;
import com.example.backend.models.dtos.LoginRequest;
import com.example.backend.models.dtos.SignupRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User convert(LoginRequest loginRequest);
    User convert(SignupRequest loginRequest);
    LoginRequest convert2(SignupRequest loginRequest);
}
