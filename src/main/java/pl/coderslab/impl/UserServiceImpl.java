package pl.coderslab.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.UserDto;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.User;
import pl.coderslab.model.Role;
import pl.coderslab.repository.RoleRepository;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.UserService;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl extends GenericServiceImpl<UserDto, User, UserRepository> implements UserService {

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        super(userRepository);
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto findByUserName(String username) {
        return convertToObjectDTO(repository.findByUsername(username), UserDto.class);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_USER");
        userDto.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return this.create(userDto);
    }

}