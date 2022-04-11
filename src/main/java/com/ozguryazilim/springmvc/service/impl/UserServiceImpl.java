package com.ozguryazilim.springmvc.service.impl;

import com.ozguryazilim.springmvc.model.Role;
import com.ozguryazilim.springmvc.model.User;
import com.ozguryazilim.springmvc.repository.RoleRepository;
import com.ozguryazilim.springmvc.repository.UserRepository;
import com.ozguryazilim.springmvc.service.UserService;
import com.ozguryazilim.springmvc.web.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        Role role = roleRepository.findRoleByName("ROLE_USER");
        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(), passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(role));
        return userRepository.save(user);
    }

    @Override
    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByEmail(username);
        return user.getId();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public boolean isAdmin(org.springframework.security.core.userdetails.User user) {
        List<Object> authorities = Arrays.asList(user.getAuthorities().stream().toArray());
        for(Object authority: authorities){
            if(authority.equals("ROLE_ADMIN")){
                return true;
            }
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority>  mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

    }
}
