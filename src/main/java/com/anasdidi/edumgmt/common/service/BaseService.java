/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.service;

import com.anasdidi.edumgmt.common.entity.BaseEntity;
import java.util.UUID;

public abstract class BaseService {

  protected Boolean[] checkRecordMatched(BaseEntity entity, UUID id, Integer version) {
    boolean idMatched = entity.getId().compareTo(id) == 0;
    boolean versionMatched = entity.getVersion().compareTo(version) == 0;
    return new Boolean[] {idMatched, versionMatched};
  }
}
