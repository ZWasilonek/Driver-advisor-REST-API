package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.RoleDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.Role;
import pl.coderslab.repository.RoleRepository;
import pl.coderslab.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        return convertToObjectDTO(roleRepository.save(convertToEntity(roleDto)));
    }

    @Override
    public RoleDto findRoleById(Long roleId) throws EntityNotFoundException {
        return convertToObjectDTO(findRoleOrThrowException(roleId));
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto) throws EntityNotFoundException {
        findRoleOrThrowException(roleDto.getId());
        Role updated = roleRepository.save(convertToEntity(roleDto));
        return convertToObjectDTO(updated);
    }

    @Override
    public boolean removeRoleById(Long roleId) throws EntityNotFoundException {
        Role founded = findRoleOrThrowException(roleId);
        roleRepository.delete(founded);
        return true;
    }

    @Override
    public RoleDto getByName(String roleName) {
        return convertToObjectDTO(roleRepository.findByName(roleName));
    }

    @Override
    public Role convertToEntity(RoleDto roleDto) {
        return new ModelMapper().map(roleDto, Role.class);
    }

    @Override
    public RoleDto convertToObjectDTO(Role role) {
        return new ModelMapper().map(role, RoleDto.class);
    }

    private Role findRoleOrThrowException(Long roleId) throws EntityNotFoundException {
        return roleRepository.findById(roleId).orElseThrow(
                () -> new EntityNotFoundException(Role.class, "id", roleId.toString()));
    }

}
