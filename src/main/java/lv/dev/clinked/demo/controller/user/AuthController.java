package lv.dev.clinked.demo.controller.user;

import lv.dev.clinked.demo.infra.security.JwtTokenProvider;
import lv.dev.clinked.demo.infra.security.SecurityConfig;
import lv.dev.clinked.demo.payloads.auth.JwtAuthenticationResponse;
import lv.dev.clinked.demo.payloads.auth.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final SecurityConfig securityConfig;

    @Autowired
    AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, SecurityConfig securityConfig) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.securityConfig = securityConfig;
    }

    @PostMapping("/signin")
    ResponseEntity<JwtAuthenticationResponse> authenticateUser(HttpServletRequest httpServletRequest,
                                                                      HttpServletResponse httpServletResponse,
                                                                      @Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationOf(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        rememberUser(httpServletRequest, httpServletResponse, authentication);
        String authToken = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(authToken));
    }

    private Authentication authenticationOf(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
    }

    private void rememberUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        RememberMeServices rememberMeServices = securityConfig.getHttpSecurity().getSharedObject(RememberMeServices.class);
        rememberMeServices.loginSuccess(httpServletRequest, httpServletResponse, authentication);
    }

}
