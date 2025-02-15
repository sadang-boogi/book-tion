package com.howread.user.controller;

import com.howread.jwt.JwtUtil;
import com.howread.jwt.service.JwtService;
import com.howread.user.controller.request.SocialLoginRequest;
import com.howread.user.controller.response.JwtResponse;
import com.howread.user.controller.response.UriResponse;
import com.howread.user.service.LoginService;
import com.howread.user.service.dto.AuthClaims;
import com.howread.user.util.SocialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/login/oauth", produces = "application/json")
public class LoginController {
    private final LoginService loginService;
    private final JwtService jwtService;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "OAuth URL 생성",
            description = "OAuth 인증을 위한 URL을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OAuth 인증 URL을 반환합니다.")
            }
    )
    @GetMapping("/{provider}/authorize")
    public ResponseEntity<UriResponse> getAuthorizationUrl(
            @PathVariable SocialType provider,
            @RequestParam String redirectUri
    ) {
        String authorizationUrl = loginService.getAuthorizationUrl(provider, redirectUri);
        return ResponseEntity.ok(new UriResponse(authorizationUrl));
    }

    @Operation(
            summary = "소셜 로그인",
            description = "소셜 프로바이더를 통해 로그인하고 JWT 토큰을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "JWT 토큰을 반환합니다.")
            }
    )
    @PostMapping("/{provider}")
    public ResponseEntity<JwtResponse> socialLogin(
            @RequestBody SocialLoginRequest body,
            @PathVariable SocialType provider
    ) {
        AuthClaims auth = loginService.socialLogin(body.getCode(), provider, body.getRedirectUri());
        String accessToken = jwtUtil.createAccessToken(auth);
        String refreshToken = jwtUtil.createRefreshToken(auth);

        jwtService.saveRefreshToken(refreshToken, auth.getUserId());

        JwtResponse jwtResponse = JwtResponse.builder()
                .token(accessToken)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(jwtResponse);
    }
}
