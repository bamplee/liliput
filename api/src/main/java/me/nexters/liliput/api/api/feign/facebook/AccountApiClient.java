package me.nexters.liliput.api.api.feign.facebook;

import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitAccessResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitProfileResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.DeleteAccountResponseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "accountKitApi", url = "https://graph.accountkit.com/v1.1", fallbackFactory = AccountApiClientFallbackFactory.class)
public interface AccountApiClient {
    @GetMapping(value = "/access_token", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    AccountKitAccessResponse getAccessToken(@RequestParam("grant_type") String grantType,
                                            @RequestParam("code") String code,
                                            @RequestParam("access_token") String accessToken);

    @GetMapping(value = "/me/", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    AccountKitProfileResponse validateAccessToken(@RequestParam("access_token") String accessToken,
                                                  @RequestParam("appsecret_proof") String appSecretRoot);

    @GetMapping(value = "/logout", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    void logout(@RequestParam("access_token") String accessToken);

    @GetMapping(value = "/{account_id}/invalidate_all_tokens", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    void invalidateAllTokens(@PathVariable("account_id") String accountId, @RequestParam("access_token") String appSecret);

    @PostMapping(value = "/{account_id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    DeleteAccountResponseResponse deleteAccount(@PathVariable("account_id") String accountId,
                                                @RequestParam("access_token") String appSecret);
}
