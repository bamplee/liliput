package me.nexters.liliput.api.domain.service;

import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitAccessResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitProfileResponse;
import me.nexters.liliput.api.domain.dto.UserModel;
import me.nexters.liliput.api.infrastructure.persistence.jpa.entity.SocialUser;
import me.nexters.liliput.api.infrastructure.persistence.jpa.repository.SocialUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SocialUserServiceImpl implements SocialUserService {
    private final AccountKitService accountKitService;
    private final SocialUserRepository socialUserRepository;
    private final PasswordEncoder passwordEncoder;

    public SocialUserServiceImpl(AccountKitService accountKitService,
                                 SocialUserRepository socialUserRepository,
                                 PasswordEncoder passwordEncoder) {
        this.accountKitService = accountKitService;
        this.socialUserRepository = socialUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel joinByAccountKitUser(String code) {
        AccountKitAccessResponse accessToken = accountKitService.validateAuthorizationCode(code);
        AccountKitProfileResponse profile = accountKitService.getProfile(accessToken.getAccessToken());
        SocialUser user = socialUserRepository.findByProviderUserId(String.valueOf(profile.getId()));
        if (!Objects.isNull(user)) {
            return transform(user);
        }
        SocialUser socialUser = new SocialUser();
        socialUser.setProviderUserId(String.valueOf(profile.getId()));
        socialUser.setPassword(passwordEncoder.encode(accessToken.getAccessToken()));
        socialUser.setPhone(Optional.ofNullable(profile.getPhone()
                                                       .getNumber())
                                    .orElse(""));
        socialUser.setEmail(Optional.ofNullable(profile.getEmail()
                                                       .getAddress())
                                    .orElse(""));
        socialUser.setProviderType("FACEBOOK");
        socialUser.setUserType("ROLE_USER");
        return transform(socialUserRepository.save(socialUser));
    }

    @Override
    public UserModel findByProviderUserId(String providerUserId) {
        SocialUser user = socialUserRepository.findByProviderUserId(providerUserId);
        if (Objects.isNull(user)) {
            return this.joinByAccountKitUser(providerUserId);
//            throw new UsernameNotFoundException("UsernameNotFound [" + providerUserId + "]");
        }
        return transform(user);
    }

    private UserModel transform(SocialUser socialUser) {
        UserModel userModel = new UserModel();
        userModel.setUserId(socialUser.getProviderUserId());
        userModel.setUserEmail(socialUser.getEmail());
        userModel.setUserPhone(socialUser.getPhone());
        userModel.setProviderType(socialUser.getProviderType());
        userModel.setRole(socialUser.getUserType());
        return userModel;
    }
}
