package pl.coderslab.service;

import pl.coderslab.dto.RoleDto;
import pl.coderslab.model.Role;

public interface RoleService {

    RoleDto getByName(String roleName);
    Role convertToEntity(RoleDto roleDto);
    RoleDto convertToObjectDTO(Role role);

}
