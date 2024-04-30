package com.shop.shop.controllers;

import com.shop.shop.application.GetUserService;
import com.shop.shop.application.SignupService;
import com.shop.shop.dtos.SignupRequestDto;
import com.shop.shop.dtos.SignupResultDto;
import com.shop.shop.dtos.UserDto;
import com.shop.shop.models.User;
import com.shop.shop.models.UserId;
import com.shop.shop.security.AuthUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final GetUserService getUserService;

    private final SignupService signupService;

    public UserController(GetUserService getUserService, SignupService signupService) {
        this.getUserService = getUserService;
        this.signupService = signupService;
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        UserId id = new UserId(authUser.id());
        User user = getUserService.getUser(id);
        return UserDto.of(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResultDto signup(
            @Valid @RequestBody SignupRequestDto signupRequestDto
    ) {
        String accessToken = signupService.signup(
                signupRequestDto.email().trim(),
                signupRequestDto.name().trim(),
                signupRequestDto.password().trim()
        );

        return new SignupResultDto(accessToken);
    }
}