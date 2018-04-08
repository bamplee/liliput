package me.nexters.liliput.api.interfaces.v1;

import me.nexters.liliput.api.domain.dto.UserModel;
import me.nexters.liliput.api.domain.service.SocialUserService;
import me.nexters.liliput.api.infrastructure.persistence.jpa.entity.SocialUser;
import me.nexters.liliput.api.interfaces.v1.dto.request.V1SocialUserJoinRequest;
import me.nexters.liliput.api.interfaces.v1.dto.response.AuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/users")
public class V1UserController {
    private final SocialUserService socialUserService;
    private final AuthenticationManager authenticationManager;

    public V1UserController(SocialUserService socialUserService,
                            AuthenticationManager authenticationManager) {
        this.socialUserService = socialUserService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public UserModel join(@RequestBody V1SocialUserJoinRequest request) {
        return socialUserService.joinByAccountKitUser(request.getCode());
    }
}
