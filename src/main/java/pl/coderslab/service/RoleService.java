package pl.coderslab.service;

import pl.coderslab.dto.RoleDto;
import pl.coderslab.model.Role;

public interface RoleService {

    RoleDto createRole(RoleDto roleDto);
    RoleDto findRoleById(Long roleId);
    RoleDto updateRole(RoleDto roleDto);
    boolean removeRoleById(Long roleId);

    RoleDto getByName(String roleName);
    Role convertToEntity(RoleDto roleDto);
    RoleDto convertToObjectDTO(Role role);

}
