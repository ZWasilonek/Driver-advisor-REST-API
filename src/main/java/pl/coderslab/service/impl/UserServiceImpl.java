package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.RoleDto;
import pl.coderslab.dto.UserDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.User;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.*;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
//    private final TrainingService trainingService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
//        this.trainingService = trainingService;
    }

    @Override
    public UserDto findByUserName(String username) {
        return convertToObjectDTO(userRepository.findByUsername(username));
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setEnabled(1);
        RoleDto userRole = roleService.getByName("ROLE_USER");
        userDto.setRoles(new HashSet<RoleDto>(Arrays.asList(userRole)));
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
        UserDto userDto = new ModelMapper().map(user, UserDto.class);
//        Set<Training> userTraining = user.getTraining();
//        if (userTraining != null) {
//            Set<TrainingDto> trainingsWithAnswersFileURL = userTraining.stream()
//                    .map(training -> trainingService.convertToObjectDTO(training))
//                    .collect(Collectors.toSet());
//            userDto.setTraining(trainingsWithAnswersFileURL);
//        }
        return userDto;
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