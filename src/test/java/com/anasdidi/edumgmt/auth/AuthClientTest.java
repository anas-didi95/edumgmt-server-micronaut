/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.anasdidi.edumgmt.auth.client.AuthClient;
import com.anasdidi.edumgmt.common.factory.CommonProps;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

@MicronautTest(transactional = false)
public class AuthClientTest {

  @Inject private CommonProps commonProps;
  @Inject private AuthClient authClient;

  @Test
  void testGetAccessToken_Success() throws ParseException {
    UsernamePasswordCredentials creds =
        new UsernamePasswordCredentials(
            commonProps.getTestUser().username(), commonProps.getTestUser().password());
    HttpResponse<BearerAccessRefreshToken> response = authClient.login(creds);
    assertEquals(HttpStatus.OK, response.status());

    BearerAccessRefreshToken resBody = response.body();
    assertEquals(commonProps.getTestUser().username(), resBody.getUsername());
    assertNotNull(resBody.getAccessToken());
    // assertNotNull(resBody.getRefreshToken());
    assertTrue(JWTParser.parse(resBody.getAccessToken()) instanceof SignedJWT);
  }
}
