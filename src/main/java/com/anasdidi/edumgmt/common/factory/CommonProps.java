/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.factory;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import java.util.Set;

@ConfigurationProperties("edumgmt")
public class CommonProps {

  @ConfigurationProperties("basic-auth")
  public static record BasicAuth(String username, String password) {}

  private BasicAuth basicAuth;

  void setBasicAuth(BasicAuth basicAuth) {
    this.basicAuth = basicAuth;
  }

  public BasicAuth getBasicAuth() {
    return basicAuth;
  }

  @ConfigurationProperties("super-admin")
  public static record SuperAdmin(String password) {}

  private SuperAdmin superAdmin;

  public SuperAdmin getSuperAdmin() {
    return superAdmin;
  }

  public void setSuperAdmin(SuperAdmin superAdmin) {
    this.superAdmin = superAdmin;
  }

  @ConfigurationProperties("test-user")
  @Requires(env = "test")
  public static record TestUser(String username, String password, Set<String> roles) {}

  private TestUser testUser;

  public TestUser getTestUser() {
    return testUser;
  }

  public void setTestUser(TestUser testUser) {
    this.testUser = testUser;
  }
}
