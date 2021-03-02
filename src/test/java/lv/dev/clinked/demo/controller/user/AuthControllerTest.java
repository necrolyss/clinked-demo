package lv.dev.clinked.demo.controller.user;

import lv.dev.clinked.demo.infra.security.JwtTokenProvider;
import lv.dev.clinked.demo.infra.security.SecurityConfig;
import lv.dev.clinked.demo.payloads.auth.JwtAuthenticationResponse;
import lv.dev.clinked.demo.payloads.auth.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    private static final String USER_EMAIL = "v.pupkin@mail.ru";
    private static final String USER_PASSWORD = "password";
    private static final String JWT_TOKEN = "w475tcwyn75tgwciec";
    
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider tokenProvider;
    @Mock
    private SecurityConfig securityConfig;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RememberMeServices rememberMeService;
    @Mock
    private ObjectPostProcessor<Object> postProcessor;
    @Mock
    private AuthenticationManagerBuilder authManagerBuilder;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        HttpSecurity httpSecurity = new HttpSecurity(postProcessor, authManagerBuilder, Collections.emptyMap());
        httpSecurity.setSharedObject(RememberMeServices.class, rememberMeService);
        given(securityConfig.getHttpSecurity()).willReturn(httpSecurity);
    }

    @Test
    void authenticatesUser() {
        LoginRequest loginRequest = new LoginRequest(USER_EMAIL, USER_PASSWORD);
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(USER_EMAIL, USER_PASSWORD);
        given(tokenProvider.generateToken(authentication)).willReturn(JWT_TOKEN);
        given(authenticationManager.authenticate(authenticationToken)).willReturn(authentication);

        ResponseEntity<JwtAuthenticationResponse> responseEntity = authController.
                authenticateUser(httpServletRequest, httpServletResponse, loginRequest);

        verify(rememberMeService).loginSuccess(httpServletRequest, httpServletResponse, authentication);
        assertThat(responseEntity).usingRecursiveComparison()
                .isEqualTo(ResponseEntity.ok(new JwtAuthenticationResponse(JWT_TOKEN)));
    }
}