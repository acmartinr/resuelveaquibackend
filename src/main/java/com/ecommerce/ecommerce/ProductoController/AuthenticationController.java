package com.ecommerce.ecommerce.ProductoController;

import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ecommerce.ecommerce.Config.CookieAuthenticationFilter;
import com.ecommerce.ecommerce.Models.SignUp;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Services.AuthenticationService;
import com.ecommerce.ecommerce.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(UserService userService,
                                    AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<User> signIn(@AuthenticationPrincipal User user,
                                       HttpServletResponse servletResponse) {

        Cookie authCookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME, authenticationService.createToken(user));
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        authCookie.setMaxAge((int) Duration.of(1, ChronoUnit.DAYS).toMinutes());
        authCookie.setPath("/");

        servletResponse.addCookie(authCookie);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody @Valid SignUp user) {
        User createdUser = userService.signUp(user);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId() + "/profile")).body(createdUser);
    }

    @PostMapping("/signOut")
    public ResponseEntity<Void> signOut(@AuthenticationPrincipal User user) {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
}
