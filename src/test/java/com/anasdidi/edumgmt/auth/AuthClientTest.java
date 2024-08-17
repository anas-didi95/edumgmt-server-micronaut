/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.anasdidi.edumgmt.auth.client.AuthClient;
import com.anasdidi.edumgmt.auth.dto.LogoutUserDTO;
import com.anasdidi.edumgmt.auth.repository.UserTokenRepository;
import com.anasdidi.edumgmt.common.factory.CommonProps;
import com.anasdidi.edumgmt.common.util.Constant;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.endpoints.TokenRefreshRequest;
import io.micronaut.security.token.render.AccessRefreshToken;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

@MicronautTest(transactional = false)
public class AuthClientTest {

  @Inject
  @Client("/edumgmt/auth/logout")
  private HttpClient logoutClient;

  @Inject private CommonProps commonProps;
  @Inject private AuthClient authClient;
  @Inject private UserTokenRepository userTokenRepository;

  @Test
  void testLogin_Success() throws ParseException {
    UsernamePasswordCredentials creds =
        new UsernamePasswordCredentials(
            commonProps.getTestUser().username(), commonProps.getTestUser().password());
    HttpResponse<BearerAccessRefreshToken> response = authClient.login(creds);
    assertEquals(HttpStatus.OK, response.status());

    BearerAccessRefreshToken resBody = response.body();
    assertEquals(commonProps.getTestUser().username(), resBody.getUsername());
    assertNotNull(resBody.getAccessToken());
    assertNotNull(resBody.getRefreshToken());
    assertTrue(JWTParser.parse(resBody.getAccessToken()) instanceof SignedJWT);

    System.out.println("accessToken\n" + resBody.getAccessToken());
    System.out.println("refreshToken\n" + resBody.getRefreshToken());
  }

  @Test
  void testRefresh_Success() throws InterruptedException {
    long oldCount = userTokenRepository.count();
    UsernamePasswordCredentials creds =
        new UsernamePasswordCredentials(
            commonProps.getTestUser().username(), commonProps.getTestUser().password());
    HttpResponse<BearerAccessRefreshToken> response = authClient.login(creds);
    BearerAccessRefreshToken resBody = response.body();
    Thread.sleep(3000);
    assertEquals(oldCount + 1, userTokenRepository.count());

    Thread.sleep(1000); // sleep for one second to give time for the issued at `iat` Claim to change
    HttpResponse<AccessRefreshToken> response2 =
        authClient.refresh(
            new TokenRefreshRequest(
                TokenRefreshRequest.GRANT_TYPE_REFRESH_TOKEN, resBody.getRefreshToken()));
    assertEquals(HttpStatus.OK, response2.status());

    AccessRefreshToken resBody2 = response2.body();
    assertNotNull(resBody2.getAccessToken());
    assertNotEquals(resBody.getAccessToken(), resBody2.getAccessToken());
  }

  @Test
  void testLogout_Success() throws InterruptedException {
    userTokenRepository.deleteAll();
    long oldCount = userTokenRepository.count();
    UsernamePasswordCredentials creds1 =
        new UsernamePasswordCredentials(
            Constant.SUPERADMIN_USER, commonProps.getSuperAdmin().password());
    authClient.login(creds1);

    UsernamePasswordCredentials creds =
        new UsernamePasswordCredentials(
            commonProps.getTestUser().username(), commonProps.getTestUser().password());
    HttpResponse<BearerAccessRefreshToken> response = authClient.login(creds);
    assertEquals(oldCount + 2, userTokenRepository.count());

    BearerAccessRefreshToken resBody = response.body();
    HttpResponse<LogoutUserDTO> response2 =
        logoutClient
            .toBlocking()
            .exchange(
                HttpRequest.POST("", null).bearerAuth(resBody.getAccessToken()),
                LogoutUserDTO.class);
    assertEquals(HttpStatus.OK, response2.status());

    LogoutUserDTO resBody2 = response2.body();
    assertEquals(1, resBody2.totalRevokedToken());
  }
}
