package com.howread.user.controller;

import com.howread.auth.annotation.Authenticated;
import com.howread.auth.annotation.LoginRequired;
import com.howread.user.controller.request.UserUpdateRequest;
import com.howread.user.controller.response.UserResponse;
import com.howread.user.service.UserService;
import com.howread.user.service.command.UserUpdateCommand;
import com.howread.user.service.dto.AuthClaims;
import com.howread.user.service.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @LoginRequired
    @GetMapping("/me")
    @Operation(summary = "현재 사용자가 자신의 정보 조회", description = "AuthClaims 의 userId로 유저를 조회한다.")
    public ResponseEntity<UserResponse> getMe(
            @Parameter(hidden = true) @Authenticated AuthClaims authClaims) {
        UserDto user = userService.getUserById(authClaims.getUserId());
        return ResponseEntity.ok(UserResponse.fromDto(user));
    }

    @LoginRequired
    @PutMapping("/me")
    @Operation(summary = "현재 사용자가 자신의 정보 수정", description = "AuthClaims 의 userId로 유저를 수정한다.")
    public ResponseEntity<UserResponse> updateMe(
            @Parameter(hidden = true) @Authenticated AuthClaims authClaims,
            @Valid @RequestBody UserUpdateRequest body) {
        UserDto updatedUser = userService.updateUser(UserUpdateCommand.from(body, authClaims.getUserId()));
        return ResponseEntity.ok(UserResponse.fromDto(updatedUser));
    }
}
