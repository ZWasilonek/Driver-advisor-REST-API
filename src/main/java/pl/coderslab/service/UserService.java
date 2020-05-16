package pl.coderslab.service;

import pl.coderslab.model.User;
import pl.coderslab.service.generic.GenericService;

public interface UserService extends GenericService<User> {
    User findByUserName(String name);

    User saveUser(User user);
}
