/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.entity.Attendance;
import com.anasdidi.edumgmt.attendance.repository.AttendanceRepository;
import com.anasdidi.edumgmt.attendance.repository.AttendanceStudentRepository;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@MicronautTest(transactional = false)
public class AttendanceControllerTest {

  @Inject
  @Client("/edumgmt/attendance")
  private HttpClient httpClient;

  @Inject private JsonMapper jsonMapper;
  @Inject private AttendanceRepository attendanceRepository;
  @Inject private StudentRepository studentRepository;
  @Inject private AttendanceStudentRepository attendanceStudentRepository;

  @BeforeEach
  void beforeEach() {
    attendanceStudentRepository.deleteAll();
    studentRepository.deleteAll();
    attendanceRepository.deleteAll();
  }

  @ParameterizedTest
  @CsvSource({"createAttendance-input.json"})
  void testCreateAttendanceSuccess(String input) {
    CreateAttendanceDTO reqBody = null;

    try (InputStream iFile = getFile(input)) {
      reqBody = jsonMapper.readValue(iFile, CreateAttendanceDTO.class);
    } catch (Exception e) {
      fail(e);
    }

    HttpResponse<ViewAttendanceDTO> response =
        httpClient.toBlocking().exchange(HttpRequest.POST("", reqBody), ViewAttendanceDTO.class);
    assertEquals(HttpStatus.CREATED, response.getStatus());

    ViewAttendanceDTO resBody = response.body();
    assertNotNull(resBody.id());
    assertNotNull(resBody.createdBy());
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
  void testCreateAttendanceStudentSuccess(String s1, String s2) {
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
                HttpRequest.POST("/" + attendance.getId(), reqBody),
                ViewAttendanceStudentDTO.class);
    assertEquals(HttpStatus.OK, response.status());

    ViewAttendanceStudentDTO resBody = response.body();
    assertEquals(attendance.getDate(), resBody.date());
    assertEquals(student.getName(), resBody.studentName());
  }

  private InputStream getFile(String fileName) {
    return this.getClass().getResourceAsStream(String.format("/testcase/attendance/%s", fileName));
  }
}
