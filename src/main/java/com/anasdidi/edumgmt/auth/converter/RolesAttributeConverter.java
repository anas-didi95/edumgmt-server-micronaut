/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.converter;

import jakarta.inject.Singleton;
import jakarta.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class RolesAttributeConverter implements AttributeConverter<Set<String>, String> {

  private static final Logger logger = LoggerFactory.getLogger(RolesAttributeConverter.class);
  private static final String DELIMITER = ";";

  @Override
  public String convertToDatabaseColumn(Set<String> attribute) {
    logger.trace("[convertToDatabaseColumn] attribute={}", attribute);
    return attribute != null ? String.join(DELIMITER, attribute) : "";
  }

  @Override
  public Set<String> convertToEntityAttribute(String dbData) {
    logger.trace("[convertToEntityAttribute] dbData={}", dbData);
    return !dbData.isBlank()
        ? Set.copyOf(Arrays.asList(dbData.split(DELIMITER)))
        : Collections.emptySet();
  }
}
