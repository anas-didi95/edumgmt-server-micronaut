/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.anasdidi.edumgmt.auth.client.AuthClient;
import com.anasdidi.edumgmt.auth.util.UserConstants;
import com.anasdidi.edumgmt.common.factory.CommonProps;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import jakarta.inject.Inject;
import java.io.InputStream;
import java.util.Optional;

public abstract class BaseControllerTest {

  @Inject private AuthClient authClient;
  @Inject private CommonProps commonProps;
  private String accessToken;
  private ModuleTest moduleTest;

  protected InputStream getFile(String fileName) {
    return this.getClass().getResourceAsStream(String.format(moduleTest.filePathFormat, fileName));
  }

  protected String getAccessToken() {
    return getAccessToken(false);
  }

  protected String getAccessToken(boolean isSuperAdmin) {
    String username =
        isSuperAdmin ? UserConstants.SuperAdmin.ID : commonProps.getTestUser().username();
    String password =
        isSuperAdmin
            ? commonProps.getSuperAdmin().password()
            : commonProps.getTestUser().password();

    return Optional.ofNullable(accessToken)
        .orElseGet(
            () -> {
              UsernamePasswordCredentials credentials =
                  new UsernamePasswordCredentials(username, password);
              HttpResponse<BearerAccessRefreshToken> response = authClient.login(credentials);
              assertEquals(HttpStatus.OK, response.getStatus());
              return response.body().getAccessToken();
            });
  }

  protected void setModuleTest(ModuleTest moduleTest) {
    this.moduleTest = moduleTest;
  }

  protected enum ModuleTest {
    ATTENDANCE("/testcase/attendance/%s"),
    AUTH("/testcase/auth/%s"),
    STUDENT("/testcase/student/%s");

    public final String filePathFormat;

    private ModuleTest(String filePathFormat) {
      this.filePathFormat = filePathFormat;
    }
  }
}
