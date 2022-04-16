package com.ozguryazilim.springmvc.service.impl;

import com.ozguryazilim.springmvc.model.Role;
import com.ozguryazilim.springmvc.model.User;
import com.ozguryazilim.springmvc.repository.AnimalRepository;
import com.ozguryazilim.springmvc.repository.RoleRepository;
import com.ozguryazilim.springmvc.repository.UserRepository;
import com.ozguryazilim.springmvc.service.UserService;
import com.ozguryazilim.springmvc.web.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.event.annotation.AfterTestExecution;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AnimalRepository animalRepository;


    @Sql(scripts = "/animal_service_create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/animal_service_delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void give_username_then_return_userId() {
        String username = "test-email@hotmail.com";
        Long id = userService.getUserIdByUsername(username);
        assertThat(id).isNotNull().isEqualTo(1L);
    }

    @Sql(scripts = "/animal_service_create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/animal_service_delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void getUserByUsername() {
        String username = "test-email@hotmail.com";
        User user = userService.getUserByUsername(username);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test-email@hotmail.com");
        assertThat(user.getFirstName()).isEqualTo("test-name");
        assertThat(user.getLastName()).isEqualTo("test-surname");
    }


}