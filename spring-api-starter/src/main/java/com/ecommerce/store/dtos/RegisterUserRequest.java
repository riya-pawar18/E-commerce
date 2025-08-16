package com.ecommerce.store.dtos;

import com.ecommerce.store.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest
{
    @NotBlank(message= "Name cannot be blank")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;
    @NotBlank(message= "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Lowercase
    private String email;
    @NotBlank(message= "Password cannot be blank")
    @Size(min=6, max = 255, message = "Password must be between 6-25 characters")
    private String password;

}
