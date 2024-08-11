/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.UpdateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import java.util.UUID;

public interface IUserService {

  UUID createUser(CreateUserDTO dto);

  ViewUserDTO viewUser(UUID id);

  UUID updateUser(UUID id, UpdateUserDTO dto);
}
