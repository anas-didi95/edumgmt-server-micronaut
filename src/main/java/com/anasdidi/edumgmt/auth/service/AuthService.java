/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.auth.dto.LogoutUserDTO;
import com.anasdidi.edumgmt.common.aspect.TraceLog;
import java.security.Principal;

@TraceLog
public interface AuthService {

  LogoutUserDTO logout(Principal principal);
}
