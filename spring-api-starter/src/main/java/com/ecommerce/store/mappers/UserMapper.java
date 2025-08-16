package com.ecommerce.store.mappers;

import com.ecommerce.store.dtos.RegisterUserRequest;
import com.ecommerce.store.dtos.UpdateUserRequest;
import com.ecommerce.store.dtos.UserDto;
import com.ecommerce.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper
{
      //@Mapping(target="createdAt",expression = "java(java.time.LocalDateTime.now())")
      UserDto toDto(User user);
      User toEntity(RegisterUserRequest request);
      public void update(UpdateUserRequest request, @MappingTarget User user);
}
