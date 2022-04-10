package com.ozguryazilim.springmvc.service;

import com.ozguryazilim.springmvc.model.User;
import com.ozguryazilim.springmvc.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);

    Long getUserIdByUsername(String username);

    User getUserByUsername(String toString);
}
