package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.RoleDto;
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

}
