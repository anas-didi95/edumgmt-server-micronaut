/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.DeleteUserDTO;
import com.anasdidi.edumgmt.auth.dto.SearchUserDTO;
import com.anasdidi.edumgmt.auth.dto.UpdateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.auth.util.UserConstants;
import com.anasdidi.edumgmt.common.BaseControllerTest;
import com.anasdidi.edumgmt.common.factory.CommonProps;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.JsonMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.crypto.password.PasswordEncoder;

@MicronautTest(transactional = false)
class UserControllerTest extends BaseControllerTest {

  @Inject
  @Client("/edumgmt/user")
  private HttpClient userClient;

  @Inject private UserRepository userRepository;
  @Inject private JsonMapper jsonMapper;
  @Inject private CommonProps commonProps;
  @Inject private PasswordEncoder passwordEncoder;

  @BeforeEach
  void beforeEach() {
    setModuleTest(ModuleTest.AUTH);
    userRepository.deleteAll();

    User entity = new User();
    entity.setUserId(UserConstants.SuperAdmin.ID);
    entity.setPassword(passwordEncoder.encode(commonProps.getSuperAdmin().password()));
    entity.setName("SuperAdmin");
    entity.setRoles(Set.of(UserConstants.Role.SUPERADMIN));
    entity.setIsDeleted(false);
    userRepository.save(entity);
  }

  @ParameterizedTest
  @CsvSource({"createUser-input.json"})
  void testCreateUser_Success(String input) {
    CreateUserDTO reqBody = null;

    try (InputStream iFile = getFile(input)) {
      reqBody = jsonMapper.readValue(iFile, CreateUserDTO.class);
    } catch (Exception e) {
      fail(e);
    }

    HttpResponse<ViewUserDTO> response =
        userClient
            .toBlocking()
            .exchange(
                HttpRequest.POST("", reqBody).bearerAuth(getAccessToken(true)), ViewUserDTO.class);
    assertEquals(HttpStatus.CREATED, response.status());

    ViewUserDTO resBody = response.body();
    assertNotNull(resBody.id());
    assertEquals(UserConstants.SuperAdmin.ID, resBody.createdBy());
    assertNotNull(resBody.createdDate());
    assertEquals(resBody.createdBy(), resBody.updatedBy());
    assertEquals(resBody.createdDate(), resBody.updatedDate());
    assertEquals(0, resBody.version());
    assertEquals(false, resBody.isDeleted());
    assertEquals(reqBody.userId(), resBody.userId());
    assertEquals(reqBody.name().toUpperCase(), resBody.name());
    assertTrue(reqBody.roles().containsAll(resBody.roles()));
  }

  @ParameterizedTest
  @CsvSource({"createUser-input.json"})
  void testUpdateUser_Success(String input) {
    User entity = null;

    try (InputStream is = getFile(input)) {
      entity = userRepository.save(jsonMapper.readValue(is, User.class));
    } catch (Exception e) {
      fail(e);
    }

    String newName = "abc" + System.currentTimeMillis();
    UpdateUserDTO reqBody = new UpdateUserDTO(entity.getId(), entity.getVersion(), newName);
    HttpResponse<ViewUserDTO> response =
        userClient
            .toBlocking()
            .exchange(
                HttpRequest.PUT("/" + entity.getId(), reqBody).bearerAuth(getAccessToken(true)),
                ViewUserDTO.class);
    assertEquals(HttpStatus.OK, response.status());

    ViewUserDTO resBody = response.body();
    assertEquals(UserConstants.SuperAdmin.ID, resBody.updatedBy());
    assertTrue(resBody.updatedDate().compareTo(resBody.createdDate()) > 0);
    assertEquals(1, resBody.version());
    assertEquals(newName.toUpperCase(), resBody.name());
  }

  @ParameterizedTest
  @CsvSource({"createUser-input.json"})
  void testDeleteUser_Success(String input) {
    User entity = null;

    try (InputStream is = getFile(input)) {
      entity = userRepository.save(jsonMapper.readValue(is, User.class));
    } catch (Exception e) {
      fail(e);
    }

    DeleteUserDTO reqBody = new DeleteUserDTO(entity.getId(), entity.getVersion());
    HttpResponse<Void> response =
        userClient
            .toBlocking()
            .exchange(
                HttpRequest.DELETE("/" + entity.getId(), reqBody).bearerAuth(getAccessToken(true)));
    assertEquals(HttpStatus.NO_CONTENT, response.status());

    entity = userRepository.findById(entity.getId()).get();
    assertEquals(UserConstants.SuperAdmin.ID, entity.getUpdatedBy());
    assertTrue(entity.getUpdatedDate().compareTo(entity.getCreatedDate()) > 0);
    assertEquals(1, entity.getVersion());
    assertEquals(true, entity.getIsDeleted());
  }

  @ParameterizedTest
  @CsvSource({"createUser-input.json,createUser-input2.json"})
  void testSearchUser_Success(String f1, String f2) {
    Arrays.asList(f1, f2).stream()
        .forEach(
            f -> {
              User entity = null;
              try (InputStream is = getFile(f)) {
                entity = jsonMapper.readValue(is, User.class);
                entity = userRepository.save(entity);
              } catch (Exception e) {
                fail(e);
              }
            });

    HttpResponse<SearchUserDTO> response =
        userClient
            .toBlocking()
            .exchange(
                HttpRequest.GET("?page=1&size=1").bearerAuth(getAccessToken(true)),
                SearchUserDTO.class);
    assertEquals(HttpStatus.OK, response.status());

    SearchUserDTO resBody = response.body();
    assertEquals(1, resBody.pagination().page());
    assertEquals(3, resBody.pagination().totalPages());
    assertEquals(1, resBody.pagination().recordsPerPage());
    assertEquals(3, resBody.pagination().totalRecords());
    assertEquals(1, resBody.resultList().size());
  }
}
