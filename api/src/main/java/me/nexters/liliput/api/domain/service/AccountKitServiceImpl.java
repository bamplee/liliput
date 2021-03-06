package me.nexters.liliput.api.domain.service;

import me.nexters.liliput.api.api.feign.facebook.AccountApiClient;
import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitAccessResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.AccountKitProfileResponse;
import me.nexters.liliput.api.api.feign.facebook.dto.DeleteAccountResponseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class AccountKitServiceImpl implements AccountKitService {
    private static final String HASH_ALGORITHM = "HmacSHA256";
    private static final String GRANT_TYPE = "authorization_code";
    private final AccountApiClient accountApiClient;
    @Value("${spring.social.facebook.appId}")
    private String appId;
    @Value("${spring.social.facebook.appSecret}")
    private String appSecret;

    public AccountKitServiceImpl(AccountApiClient accountApiClient) {
        this.accountApiClient = accountApiClient;
    }

    public AccountKitAccessResponse validateAuthorizationCode(String code) {
        return accountApiClient.getAccessToken(GRANT_TYPE, code, generateAccessToken());
    }

    public AccountKitProfileResponse getProfile(String accessToken) {
        String hash = null;
        try {
            hash = hashMac(accessToken, appSecret);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        AccountKitProfileResponse accountKitProfileResponse = accountApiClient.validateAccessToken(accessToken, hash);
        if (isEmpty(accountKitProfileResponse.getEmail())) {
            AccountKitProfileResponse.AccountKitEmail accountKitEmail = new AccountKitProfileResponse.AccountKitEmail();
            accountKitEmail.setAddress("");
            accountKitProfileResponse.setEmail(accountKitEmail);
        }
        if (isEmpty(accountKitProfileResponse.getPhone())) {
            AccountKitProfileResponse.AccountKitPhone phone = new AccountKitProfileResponse.AccountKitPhone();
            phone.setNumber("-1");
            accountKitProfileResponse.setPhone(phone);
        }
        return accountKitProfileResponse;
    }

    public DeleteAccountResponseResponse removeAccount(String accountId) {
        return accountApiClient.deleteAccount(accountId, generateAccessToken());
    }

    private String hashMac(String text, String secretKey)
        throws SignatureException {
        try {
            Key sk = new SecretKeySpec(secretKey.getBytes(), HASH_ALGORITHM);
            Mac mac = Mac.getInstance(sk.getAlgorithm());
            mac.init(sk);
            final byte[] hmac = mac.doFinal(text.getBytes());
            return toHexString(hmac);
        } catch (NoSuchAlgorithmException e1) {// throw an exception or pick a different encryption method
            throw new SignatureException(
                "error building signature, no such algorithm in device "
                    + HASH_ALGORITHM);
        } catch (InvalidKeyException e) {
            throw new SignatureException(
                "error building signature, invalid key " + HASH_ALGORITHM);
        }
    }

    private String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return sb.toString();
    }

    private String generateAccessToken() {
        return StringUtils.join("AA|", appId, "|", appSecret);
    }
}
