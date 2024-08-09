/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.client;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import jakarta.inject.Singleton;

@Singleton
@Client("/edumgmt")
public interface AuthClient {

  @Post("/login")
  HttpResponse<BearerAccessRefreshToken> login(@Body UsernamePasswordCredentials credentials);
}
