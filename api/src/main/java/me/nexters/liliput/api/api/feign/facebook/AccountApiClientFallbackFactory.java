package me.nexters.liliput.api.api.feign.facebook;

import lombok.extern.slf4j.Slf4j;
import me.nexters.liliput.api.api.LoggingFallbackFactory;
import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitAccessResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitProfileResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.DeleteAccountResponseResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountApiClientFallbackFactory implements LoggingFallbackFactory<AccountApiClient> {
    private static final AccountApiClient FALLBACK = new AccountApiClientFallbackFactory.GraphApiFallback();

    @Override
    public AccountApiClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return null;
    }

    public static class GraphApiFallback implements AccountApiClient {
        @Override
        public AccountKitAccessResponse getAccessToken(String grant_Type, String code, String access_token) {
            return null;
        }

        @Override
        public AccountKitProfileResponse validateAccessToken(String accessToken, String appSecretRoot) {
            return null;
        }

        @Override
        public void logout(String accessToken) {
        }

        @Override
        public void invalidateAllTokens(String accountId, String appSecret) {
        }

        @Override
        public DeleteAccountResponseResponse deleteAccount(String accountId, String appSecret) {
            return null;
        }
    }
}
