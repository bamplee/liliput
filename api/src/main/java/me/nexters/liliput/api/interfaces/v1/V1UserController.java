package me.nexters.liliput.api.interfaces.v1;

import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitAccessResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitProfileResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.DeleteAccountResponseResponse;
import me.nexters.liliput.api.domain.dto.UserModel;
import me.nexters.liliput.api.domain.service.SocialUserService;
import me.nexters.liliput.api.interfaces.v1.dto.request.V1SocialUserJoinRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/users")
public class V1UserController {
    private final SocialUserService socialUserService;

    public V1UserController(SocialUserService socialUserService) {
        this.socialUserService = socialUserService;
    }

    @PostMapping
    public UserModel join(@RequestBody V1SocialUserJoinRequest request) {
        return socialUserService.joinByAccountKitUser(request.getCode());
    }
}
