package me.nexters.liliput.api.interfaces.v1;

import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitAccessResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitProfileResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.DeleteAccountResponseResponse;
import me.nexters.liliput.api.domain.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/users")
public class V1UserController {
    @NotNull
    private final UserService userService;

    public V1UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/valid")
    public AccountKitAccessResponse validateAccount(@RequestParam String code) {
        return userService.validateAuthorizationCode(code);
    }

    @GetMapping("/profile")
    public AccountKitProfileResponse getAccountProfile(@RequestParam String accessToken) {
        return userService.getProfile(accessToken);
    }

    @GetMapping("/delete")
    public DeleteAccountResponseResponse deleteAccount(@RequestParam String accountId) {
        return userService.removeAccount(accountId);
    }
}
