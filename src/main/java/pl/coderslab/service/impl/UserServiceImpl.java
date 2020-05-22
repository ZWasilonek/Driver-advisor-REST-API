package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.UserDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.Role;
import pl.coderslab.model.User;
import pl.coderslab.repository.RoleRepository;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.UserService;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto findByUserName(String username) {
        return convertToObjectDTO(userRepository.findByUsername(username));
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_USER");
        userDto.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return convertToObjectDTO(userRepository.save(convertToEntity(userDto)));
    }

    @Override
    public UserDto findUserById(Long userId) throws EntityNotFoundException {
        return convertToObjectDTO(getUserById(userId));
    }

    @Override
    public UserDto updateUser(UserDto userDto) throws EntityNotFoundException {
        getUserById(userDto.getId());
        return saveUser(userDto);
    }

    @Override
    public boolean removeUserById(Long userId) throws EntityNotFoundException {
        User founded = getUserById(userId);
        if (founded != null) {
            userRepository.delete(founded);
            return true;
        }
        return false;
    }

    @Override
    public UserDto convertToObjectDTO(User user) {
        return new ModelMapper().map(user, UserDto.class);
    }

    @Override
    public User convertToEntity(UserDto userDto) {
        return new ModelMapper().map(userDto, User.class);
    }

    private User getUserById(Long userId) throws EntityNotFoundException {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(User.class, "id", userId.toString()));
    }

}