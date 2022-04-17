package com.ozguryazilim.springmvc.web.dto;


import lombok.*;

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
