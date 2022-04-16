package com.helmesback.controllers;

import com.helmesback.domain.UserDTO;
import com.helmesback.mappers.Mapper;
import com.helmesback.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Mapper mapper;

    @GetMapping("/{userName}")
    public UserDTO getUserByUserName(@PathVariable String userName) {
        return mapper.mapUserToUserDTO(userService.getUser(userName));
    }

    @PostMapping
    public void postUser(@RequestBody UserDTO user) {
        userService.saveUser(mapper.mapUserDTOToUser(user));
    }
}
