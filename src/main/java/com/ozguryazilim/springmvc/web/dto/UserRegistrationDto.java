package com.ozguryazilim.springmvc.web.dto;

import com.ozguryazilim.springmvc.model.Role;
import com.ozguryazilim.springmvc.model.User;
import com.sun.tools.javac.util.List;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
