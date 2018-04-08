package me.nexters.liliput.api.domain.service;

import me.nexters.liliput.api.domain.dto.UserModel;

public interface SocialUserService {
    UserModel joinByAccountKitUser(String code);

    UserModel findByProviderUserId(String providerUserId);
}
