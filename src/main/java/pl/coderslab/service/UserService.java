package pl.coderslab.service;

import pl.coderslab.model.User;
import pl.coderslab.service.generic.GenericService;

public interface UserService<T> extends GenericService<T> {
    User findByUserName(String name);

    void saveUser(User user);
}
