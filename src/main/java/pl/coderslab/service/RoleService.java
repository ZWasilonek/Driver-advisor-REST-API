package pl.coderslab.service;

import pl.coderslab.dto.RoleDto;
import pl.coderslab.model.Role;
import pl.coderslab.service.generic.GenericService;

public interface RoleService extends GenericService<RoleDto, Role> {

    RoleDto getByName(String roleName);

}
