package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.RoleDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.RoleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public RoleDto createRole(@Valid @RequestBody RoleDto roleDto) {
        return roleService.createRole(roleDto);
    }

    @GetMapping("/find/{id}")
    public RoleDto findRoleById(@PathVariable("id") Long roleId)throws EntityNotFoundException {
        return roleService.findRoleById(roleId);
    }

    @PutMapping("/update")
    public RoleDto updateRole(@RequestBody RoleDto roleDto)throws EntityNotFoundException {
        return roleService.updateRole(roleDto);
    }

    @DeleteMapping("/remove/{id}")
    public boolean removeRoleById(@PathVariable("id") Long roleId)throws EntityNotFoundException {
        return roleService.removeRoleById(roleId);
    }

}