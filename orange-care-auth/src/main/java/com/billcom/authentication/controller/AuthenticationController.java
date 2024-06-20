package com.billcom.authentication.controller;

import com.billcom.authentication.config.SampleAuthManager;
import com.billcom.authentication.domains.AuthenticationResponse;
import com.billcom.authentication.domains.BscsUser;
import com.billcom.authentication.utils.JWTUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Log4j2
@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthenticationController {

    private final SampleAuthManager provider;
    private final JWTUtils jwtUtil;

    @GetMapping("/validate/{token}")
    public ResponseEntity<?> validate(@PathVariable String token) {
        log.info("Received token {}", token);
        Boolean value = false;
        try {
            value = jwtUtil.validateToken(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .build();
        }
        return ResponseEntity.ok(value);
    }

    @PostMapping("/authenticate")
    public @ResponseBody ResponseEntity<AuthenticationResponse> authenticate(@RequestBody BscsUser user) {
        log.info(user.toString());
        Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),
                user.getPassword());
        try {
            Authentication authenticatedUser = provider.authenticate(usernamePasswordAuthenticationToken);
            String token = jwtUtil.generateToken(authenticatedUser);
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .isSuccessful(authenticatedUser.isAuthenticated())
                    .comment("Successfully authenticated user")
                    .token(token)
                    .build();
            log.info(authenticationResponse.toString());
            return ResponseEntity.ok(authenticationResponse);

        } catch (BadCredentialsException e) {
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .isSuccessful(false)
                    .comment(e.getMessage())
                    .build();
            return ResponseEntity.status(400).body(authenticationResponse);
        }
    }

}
