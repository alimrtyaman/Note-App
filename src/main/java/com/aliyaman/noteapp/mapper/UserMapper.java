package com.aliyaman.noteapp.mapper;

import com.aliyaman.noteapp.dto.UserDto;
import com.aliyaman.noteapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserDto userDto);

    UserDto toDto(User user);
}
