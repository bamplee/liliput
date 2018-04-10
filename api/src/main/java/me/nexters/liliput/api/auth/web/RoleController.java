package me.nexters.liliput.api.auth.web;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @PostAuthorize("hasAuthority('USER')")
    @GetMapping("/api/v1/user/role")
    public String user(Authentication authentication) {
        System.out.println("RoleController : " + authentication.getAuthorities()
                                                               .toString());
        System.out.println("RoleController : " + authentication.getPrincipal());
        return "I'm Jwt Token User!";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/v1/user/admin")
    public String admin(Authentication authentication) {
        System.out.println("RoleController : " + authentication.getAuthorities()
                                                               .toString());
        System.out.println("RoleController : " + authentication.getPrincipal());
        return "I'm Jwt Token Admin!";
    }
}
