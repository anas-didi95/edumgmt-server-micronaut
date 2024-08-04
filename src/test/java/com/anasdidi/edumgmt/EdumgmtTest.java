/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt;

import io.micronaut.core.type.Argument;
import io.micronaut.json.JsonMapper;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.io.InputStream;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@MicronautTest
class EdumgmtTest {

  @Inject EmbeddedApplication<?> application;
  @Inject JsonMapper jsonMapper;

  @Test
  void testItWorks() {
    Assertions.assertTrue(application.isRunning());
  }

  @ParameterizedTest
  @CsvSource({"sample-input.json,sample-output.json"})
  void testReadSampleTestCase(String inputFile, String outputFile) {
    Assertions.assertEquals("sample-input.json", inputFile);
    Assertions.assertEquals("sample-output.json", outputFile);

    try (InputStream is = EdumgmtTest.class.getResourceAsStream("/testcase/" + inputFile);
        InputStream os = EdumgmtTest.class.getResourceAsStream("/testcase/" + outputFile)) {
      Map<String, String> input =
          jsonMapper.readValue(is, Argument.mapOf(Argument.STRING, Argument.STRING));
      Assertions.assertEquals("sample-input", input.get("id"));
      Assertions.assertEquals("HELLO WORLD", input.get("value"));

      Map<String, String> output =
          jsonMapper.readValue(os, Argument.mapOf(Argument.STRING, Argument.STRING));
      Assertions.assertEquals("sample-output", output.get("id"));
      Assertions.assertEquals("HI!", output.get("value"));
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }
}
