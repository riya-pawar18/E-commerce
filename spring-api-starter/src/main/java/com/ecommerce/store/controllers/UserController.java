package com.ecommerce.store.controllers;

import com.ecommerce.store.dtos.ChangePasswordRequest;
import com.ecommerce.store.dtos.RegisterUserRequest;
import com.ecommerce.store.dtos.UpdateUserRequest;
import com.ecommerce.store.dtos.UserDto;
import com.ecommerce.store.mappers.UserMapper;
import com.ecommerce.store.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    @GetMapping
    public Iterable<UserDto> getAllUsers(
            //@RequestHeader(name = "x-auth-token") String authToken,
            @RequestParam(required = false, defaultValue = "", name="sort") String sort) {
        //System.out.println(authToken);
        if(!Set.of("name","email").contains(sort))
        {
            sort= "name";
        }
        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id)
    {
       var user= userRepository.findById(id).orElse(null);
       if(user==null)
           //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           return ResponseEntity.notFound().build();

           //return new ResponseEntity<>(user,HttpStatus.OK);
        //var userDto= new UserDto(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request,
                                              UriComponentsBuilder uriBuilder)
    {
        if(userRepository.existsByEmail(request.getEmail()))
        {
            return ResponseEntity.badRequest().body(Map.of("email","Email already registered"));
        }
        var user= userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        var userDto= userMapper.toDto(user);
        var uri= uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request)
    {
        var user= userRepository.findById(id).orElse(null);
        if(user==null)
            return ResponseEntity.notFound().build();

        userMapper.update(request,user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id)
    {
        var user= userRepository.findById(id).orElse(null);
        if(user==null)
            return ResponseEntity.notFound().build();
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request)
    {
        var user= userRepository.findById(id).orElse(null);
        if(user==null)
            return ResponseEntity.notFound().build();
        if(!user.getPassword().equals(request.getOldPassword()))
        {
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }


}
