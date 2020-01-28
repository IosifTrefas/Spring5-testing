package ro.isf.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.isf.services.register.RegisterService;
import ro.isf.services.register.User;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterRestController {
    private final RegisterService registerUseCase;

    @PostMapping("/forums/{forumId}/register")
    UserResource register(
            @PathVariable("forumId") Long forumId,
            @Valid @RequestBody UserResource userResource,
            @RequestParam("sendWelcomeMail") boolean sendWelcomeMail) {

        User user = new User(
                userResource.getName(),
                userResource.getEmail());
        Long userId = registerUseCase.registerUser(user, sendWelcomeMail);

        return new UserResource(
                user.getName(),
                user.getEmail());
    }
}
