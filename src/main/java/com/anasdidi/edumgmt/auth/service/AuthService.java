/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.auth.dto.LogoutUserDTO;
import java.security.Principal;

public interface AuthService {

  LogoutUserDTO logout(Principal principal);
}
