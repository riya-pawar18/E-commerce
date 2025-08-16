package com.ecommerce.store.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserDto
{
   // @JsonIgnore
    @JsonProperty("user_id")
    private Long id;
    private String name;
    private String email;
//    @JsonInclude(JsonInclude.Include.NON_NULL)  // doesn't include null fields
//    private String phone;
//    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
//    private LocalDateTime createdAt;
}
