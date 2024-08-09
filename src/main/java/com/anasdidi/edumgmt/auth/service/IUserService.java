/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import java.util.UUID;

public interface IUserService {

  UUID createUser(CreateUserDTO reqBody);

  ViewUserDTO viewUser(UUID id);
}
