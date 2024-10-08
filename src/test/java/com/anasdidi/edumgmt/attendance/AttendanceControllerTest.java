/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.SearchAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.SearchAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.entity.Attendance;
import com.anasdidi.edumgmt.attendance.entity.AttendanceStudent;
import com.anasdidi.edumgmt.attendance.repository.AttendanceRepository;
import com.anasdidi.edumgmt.attendance.repository.AttendanceStudentRepository;
import com.anasdidi.edumgmt.common.BaseControllerTest;
import com.anasdidi.edumgmt.common.factory.CommonProps;
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
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@MicronautTest(transactional = false)
public class AttendanceControllerTest extends BaseControllerTest {

  @Inject
  @Client("/edumgmt/attendance")
  private HttpClient httpClient;

  @Inject private JsonMapper jsonMapper;
  @Inject private AttendanceRepository attendanceRepository;
  @Inject private StudentRepository studentRepository;
  @Inject private AttendanceStudentRepository attendanceStudentRepository;
  @Inject private CommonProps commonProps;

  @BeforeEach
  void beforeEach() {
    setModuleTest(ModuleTest.ATTENDANCE);
    attendanceStudentRepository.deleteAll();
    studentRepository.deleteAll();
    attendanceRepository.deleteAll();
  }

  @ParameterizedTest
  @CsvSource({"createAttendance-input.json"})
  void testCreateAttendance_Success(String input) {
    CreateAttendanceDTO reqBody = null;

    try (InputStream iFile = getFile(input)) {
      reqBody = jsonMapper.readValue(iFile, CreateAttendanceDTO.class);
    } catch (Exception e) {
      fail(e);
    }

    HttpResponse<ViewAttendanceDTO> response =
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.POST("", reqBody).bearerAuth(getAccessToken()),
                ViewAttendanceDTO.class);
    assertEquals(HttpStatus.CREATED, response.getStatus());

    ViewAttendanceDTO resBody = response.body();
    assertNotNull(resBody.id());
    assertEquals(commonProps.getTestUser().username(), resBody.createdBy());
    assertNotNull(resBody.createdDate());
    assertEquals(resBody.createdBy(), resBody.updatedBy());
    assertEquals(resBody.createdDate(), resBody.updatedDate());
    assertEquals(0, resBody.version());
    assertEquals(false, resBody.isDeleted());
    assertTrue(resBody.date().compareTo(reqBody.date()) == 0);
    assertEquals(reqBody.remark(), resBody.remark());
  }

  @ParameterizedTest
  @CsvSource({"createAttendance-input.json,createStudent-input.json"})
  void testCreateAttendanceStudent_Success(String s1, String s2) {
    Attendance attendance = null;
    Student student = null;

    try (InputStream i1 = getFile(s1);
        InputStream i2 = getFile(s2)) {
      attendance = attendanceRepository.save(jsonMapper.readValue(i1, Attendance.class));
      student = studentRepository.save(jsonMapper.readValue(i2, Student.class));
    } catch (Exception e) {
      fail(e);
    }

    CreateAttendanceStudentDTO reqBody = new CreateAttendanceStudentDTO(student.getId());
    HttpResponse<ViewAttendanceStudentDTO> response =
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.POST("/" + attendance.getId(), reqBody).bearerAuth(getAccessToken()),
                ViewAttendanceStudentDTO.class);
    assertEquals(HttpStatus.OK, response.status());

    ViewAttendanceStudentDTO resBody = response.body();
    assertEquals(attendance.getDate(), resBody.date());
    assertEquals(student.getName(), resBody.studentName());
  }

  @ParameterizedTest
  @CsvSource({"createAttendance-input.json,createAttendance-input2.json"})
  void testSearchAttendance_Success(String f1, String f2) {
    Arrays.asList(f1, f2).stream()
        .forEach(
            f -> {
              Attendance entity = null;
              try (InputStream is = getFile(f)) {
                entity = jsonMapper.readValue(is, Attendance.class);
                entity = attendanceRepository.save(entity);
              } catch (Exception e) {
                fail(e);
              }
            });

    HttpResponse<SearchAttendanceDTO> response =
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.GET("?page=1&size=1").bearerAuth(getAccessToken()),
                SearchAttendanceDTO.class);
    assertEquals(HttpStatus.OK, response.status());

    SearchAttendanceDTO resBody = response.body();
    assertEquals(1, resBody.pagination().page());
    assertEquals(2, resBody.pagination().totalPages());
    assertEquals(1, resBody.pagination().recordsPerPage());
    assertEquals(2, resBody.pagination().totalRecords());
    assertEquals(1, resBody.resultList().size());
  }

  @ParameterizedTest
  @CsvSource({"createAttendance-input.json,createStudent-input.json,createStudent-input2.json"})
  void testSearchAttendanceSuccess_Success(String f1, String f2, String f3) {
    Attendance attendance = null;
    try (InputStream s1 = getFile(f1)) {
      attendance = jsonMapper.readValue(s1, Attendance.class);
      attendance = attendanceRepository.save(attendance);
    } catch (Exception e) {
      fail(e);
    }

    final UUID attendanceId = attendance.getId();
    Arrays.asList(f2, f3).stream()
        .forEach(
            f -> {
              Student student = null;
              AttendanceStudent entity = null;
              try (InputStream is = getFile(f)) {
                student = jsonMapper.readValue(is, Student.class);
                student = studentRepository.save(student);

                entity = new AttendanceStudent();
                entity.setAttendanceId(attendanceId);
                entity.setStudentId(student.getId());
                entity.setIsDeleted(false);
                entity = attendanceStudentRepository.save(entity);
              } catch (Exception e) {
                fail(e);
              }
            });

    HttpResponse<SearchAttendanceStudentDTO> response =
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.GET("/" + attendanceId + "?page=1&size=1").bearerAuth(getAccessToken()),
                SearchAttendanceStudentDTO.class);
    assertEquals(HttpStatus.OK, response.status());

    SearchAttendanceStudentDTO resBody = response.body();
    assertEquals(1, resBody.pagination().page());
    assertEquals(2, resBody.pagination().totalPages());
    assertEquals(1, resBody.pagination().recordsPerPage());
    assertEquals(2, resBody.pagination().totalRecords());
    assertEquals(1, resBody.resultList().size());
  }
}
