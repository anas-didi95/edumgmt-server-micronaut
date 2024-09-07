/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.anasdidi.edumgmt.common.BaseControllerTest;
import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.DeleteStudentDTO;
import com.anasdidi.edumgmt.student.dto.SearchStudentDTO;
import com.anasdidi.edumgmt.student.dto.UpdateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import com.anasdidi.edumgmt.student.entity.Student;
import com.anasdidi.edumgmt.student.repository.StudentRepository;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@MicronautTest(transactional = false)
public class StudentControllerTest extends BaseControllerTest {

  @Inject
  @Client("/edumgmt/student")
  private HttpClient httpClient;

  @Inject private JsonMapper jsonMapper;
  @Inject private StudentRepository studentRepository;

  @BeforeEach
  void beforeEach() {
    setModuleTest(ModuleTest.STUDENT);
    studentRepository.deleteAll();
  }

  @ParameterizedTest
  @CsvSource({"createStudent-input.json"})
  void testCreateStudent_Success(String input) {
    CreateStudentDTO reqBody = null;

    try (InputStream iFile = getFile(input)) {
      reqBody = jsonMapper.readValue(iFile, CreateStudentDTO.class);
    } catch (Exception e) {
      fail(e);
    }

    HttpResponse<ViewStudentDTO> response =
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.POST("", reqBody).bearerAuth(getAccessToken()), ViewStudentDTO.class);
    assertEquals(HttpStatus.CREATED, response.getStatus());

    ViewStudentDTO resBody = response.body();
    assertNotNull(resBody.id());
    assertNotNull(resBody.createdBy());
    assertNotNull(resBody.createdDate());
    assertEquals(resBody.createdBy(), resBody.updatedBy());
    assertEquals(resBody.createdDate(), resBody.updatedDate());
    assertEquals(0, resBody.version());
    assertEquals(false, resBody.isDeleted());
    assertEquals(reqBody.name().toUpperCase(), resBody.name());
    assertEquals(reqBody.idNo(), resBody.idNo());
  }

  @ParameterizedTest
  @CsvSource({"createStudent-input.json"})
  void testViewStudent_Success(String file) {
    Student entity = null;

    try (InputStream is = getFile(file)) {
      entity = jsonMapper.readValue(is, Student.class);
      entity = studentRepository.save(entity);
    } catch (Exception e) {
      fail(e);
    }

    HttpResponse<ViewStudentDTO> response =
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.GET("/" + entity.getId()).bearerAuth(getAccessToken()),
                ViewStudentDTO.class);
    assertEquals(HttpStatus.OK, response.status());
    assertEquals(entity.getId(), response.body().id());
  }

  @ParameterizedTest
  @CsvSource({"createStudent-input.json"})
  void testUpdateStudent_Success(String file) {
    Student entity = null;

    try (InputStream is = getFile(file)) {
      entity = jsonMapper.readValue(is, Student.class);
      entity = studentRepository.save(entity);
    } catch (Exception e) {
      fail(e);
    }

    String newName = "abc" + System.currentTimeMillis();
    String newIdNo = "010203040506";
    UpdateStudentDTO reqBody = new UpdateStudentDTO(entity.getId(), newIdNo, newName, 0);
    HttpResponse<ViewStudentDTO> response =
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.PUT("/" + entity.getId(), reqBody).bearerAuth(getAccessToken()),
                ViewStudentDTO.class);
    assertEquals(HttpStatus.OK, response.status());

    ViewStudentDTO resBody = response.body();
    assertNotNull(resBody.updatedBy());
    assertTrue(resBody.updatedDate().compareTo(resBody.createdDate()) > 0);
    assertEquals(1, resBody.version());
    assertEquals(newName.toUpperCase(), resBody.name());
    assertEquals(newIdNo, resBody.idNo());
  }

  @ParameterizedTest
  @CsvSource({"createStudent-input.json"})
  void testDeleteStudent_Success(String file) {
    Student entity = null;

    try (InputStream is = getFile(file)) {
      entity = jsonMapper.readValue(is, Student.class);
      entity = studentRepository.save(entity);
    } catch (Exception e) {
      fail(e);
    }

    DeleteStudentDTO reqBody = new DeleteStudentDTO(entity.getId(), 0);
    HttpResponse<Void> response =
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.DELETE("/" + entity.getId(), reqBody).bearerAuth(getAccessToken()),
                Void.class);
    assertEquals(HttpStatus.NO_CONTENT, response.status());

    entity = studentRepository.findById(entity.getId()).get();
    assertNotNull(entity.getUpdatedBy());
    assertTrue(entity.getUpdatedDate().compareTo(entity.getCreatedDate()) > 0);
    assertEquals(1, entity.getVersion());
    assertEquals(true, entity.getIsDeleted());
  }

  @ParameterizedTest
  @CsvSource({"createStudent-input.json,createStudent-input2.json"})
  void testSearchStudent_Success(String f1, String f2) {
    Arrays.asList(f1, f2).stream()
        .forEach(
            f -> {
              Student entity = null;
              try (InputStream is = getFile(f)) {
                entity = jsonMapper.readValue(is, Student.class);
                entity = studentRepository.save(entity);
              } catch (Exception e) {
                fail(e);
              }
            });

    HttpResponse<SearchStudentDTO> response =
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.GET("?page=1&size=1").bearerAuth(getAccessToken()),
                SearchStudentDTO.class);
    assertEquals(HttpStatus.OK, response.status());

    SearchStudentDTO resBody = response.body();
    assertEquals(1, resBody.pagination().page());
    assertEquals(2, resBody.pagination().totalPages());
    assertEquals(1, resBody.pagination().recordsPerPage());
    assertEquals(2, resBody.pagination().totalRecords());
    assertEquals(1, resBody.resultList().size());
  }
}
