/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.DeleteUserDTO;
import com.anasdidi.edumgmt.auth.dto.SearchUserDTO;
import com.anasdidi.edumgmt.auth.dto.UpdateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import com.anasdidi.edumgmt.common.aspect.TraceLog;
import io.micronaut.data.model.Pageable;
import java.util.UUID;

@TraceLog
public interface UserService {

  UUID createUser(CreateUserDTO dto);

  ViewUserDTO viewUser(UUID id);

  UUID updateUser(UUID id, UpdateUserDTO dto);

  void deleteUser(UUID id, DeleteUserDTO dto);

  SearchUserDTO searchUser(String userId, String name, Pageable pageable);
}
