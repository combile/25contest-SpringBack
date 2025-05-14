package com.example.newai.appuser.service;

import com.example.newai.appuser.vo.AppUserDto;
import com.example.newai.appuser.vo.AppUserRequest;
import com.example.newai.appuser.vo.RedundancyCheckRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AppUserService {
    AppUserDto createAppUser(AppUserRequest appUserRequest);
    AppUserDto getAppUser(Authentication authentication);
    AppUserDto updateAppUser(Authentication authentication, AppUserRequest appUserRequest);
    void deleteAppUser(Authentication authentication);

    Boolean redundancyCheck(RedundancyCheckRequest redundancyCheckRequest);
}
