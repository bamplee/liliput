package me.nexters.liliput.api.auth.process.ajax;

import me.nexters.liliput.api.auth.process.UserDetailsImpl;
import me.nexters.liliput.api.domain.dto.UserModel;
import me.nexters.liliput.api.domain.service.SocialUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AjaxUserDetailsService implements UserDetailsService {
    @Autowired
    private SocialUserService socialUserService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserModel user = socialUserService.findByProviderUserId(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "라는 사용자가 없습니다.");
        }
        return new UserDetailsImpl(user, AuthorityUtils.createAuthorityList(user.getRole()));
    }
}
