package com.example.backend.models.mappers;

import com.example.backend.models.User;
import com.example.backend.models.dtos.LoginRequest;
import com.example.backend.models.dtos.SignupRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "email", ignore = true)
    User loginRequestToUser(LoginRequest loginRequest);

    @Mapping(target = "authorities", ignore = true)
    User signupRequestToUser(SignupRequest signupRequest);
    LoginRequest convert2(SignupRequest loginRequest);
}
