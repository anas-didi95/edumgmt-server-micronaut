/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.common.aspect.TraceLog;
import java.security.Principal;

@TraceLog
public interface AuthService {

  void logout(Principal principal);
}
