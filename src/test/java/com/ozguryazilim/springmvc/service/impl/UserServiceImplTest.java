package com.ozguryazilim.springmvc.service.impl;
import com.ozguryazilim.springmvc.model.Role;
import com.ozguryazilim.springmvc.model.User;
import com.ozguryazilim.springmvc.repository.RoleRepository;
import com.ozguryazilim.springmvc.repository.UserRepository;
import com.ozguryazilim.springmvc.service.UserService;
import com.ozguryazilim.springmvc.web.dto.UserRegistrationDto;
import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import org.springframework.security.core.userdetails.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    public void setup(){
         given(passwordEncoder.encode(ArgumentMatchers.any(String.class))).willReturn("12345678");
         user = new User();
         user.setId(1L);
         user.setFirstName("test-firstname");
         user.setLastName("test-lastname");
         user.setEmail("test-email@hotmail.com");
         user.setPassword(passwordEncoder.encode("12345678".toString()));
         user.setRoles(List.of(new Role("TEST_USER")));
    }

    @Test
    void givenUsername_whenGetUserIdByUsername_thenReturnUserId() {

        //given
        given(userRepository.findByEmail(user.getEmail())).willReturn(user);

        //when
        Long id = userService.getUserIdByUsername(user.getEmail());

        //then
        assertThat(id).isEqualTo(1L);

    }

    @Test
    void givenUsername_whenGetUserByUsername_thenReturnUserObject() {
        //given
        given(userRepository.findByEmail(user.getEmail())).willReturn(user);

        //when
        User testUser = userService.getUserByUsername(user.getEmail());

        //then
        assertThat(testUser).isNotNull();
        assertThat(testUser.getEmail()).isEqualTo("test-email@hotmail.com");
    }

    @Test
    void givenUserRegistrationDto_whenSave_thenReturnUser(){

        //given
        UserRegistrationDto registrationDto =
                UserRegistrationDto.builder()
                        .firstName("deneme-firstname")
                        .lastName("deneme-surname")
                        .password("12345678")
                        .email("deneme-email@hotmail.com")
                        .build();

        Role role = new Role();
        role.setName("ROLE_USER");
        role.setId(2L);
        given(passwordEncoder.encode(ArgumentMatchers.any(String.class))).willReturn("123456");
        User testUser = new User();
        testUser.setId(2L);
        testUser.setFirstName(registrationDto.getFirstName());
        testUser.setLastName(registrationDto.getLastName());
        testUser.setEmail(registrationDto.getEmail());
        testUser.setPassword(passwordEncoder.encode(registrationDto.getPassword().toString()));
        testUser.setRoles(List.of(role));
        given(userRepository.save(ArgumentMatchers.any(User.class))).willReturn(testUser);
        given(roleRepository.findRoleByName("ROLE_USER")).willReturn(role);

        //when
        User savedUser = userService.save(registrationDto);

        //then
        assertThat(testUser).isEqualTo(testUser);
    }


    @Test
    void givenUsername_whenloadUserByUsername_thenReturnUserDetails(){
        //given
        given(userRepository.findByEmail(user.getEmail())).willReturn(user);

        //when
        UserDetails user1 = userService.loadUserByUsername(user.getEmail());

         //then
        assertThat(user1.getUsername()).isEqualTo(user.getEmail());

    }

    @Test
    void givenUserDetails_whenIsAdmin_thenReturnBool(){
        //given
        org.springframework.security.core.userdetails.User testUser = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), userService.mapRolesToAuthorities(user.getRoles()));

        //when
        Boolean isAdmin = userService.isAdmin(testUser);

        //then
        assertThat(isAdmin).isEqualTo(false);
    }

}