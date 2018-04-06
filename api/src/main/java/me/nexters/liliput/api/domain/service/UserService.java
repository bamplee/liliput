package me.nexters.liliput.api.domain.service;

import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitAccessResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitProfileResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.DeleteAccountResponseResponse;

public interface UserService {
    AccountKitAccessResponse validateAuthorizationCode(String code);

    AccountKitProfileResponse getProfile(String accessToken);

    DeleteAccountResponseResponse removeAccount(String accountId);
}
