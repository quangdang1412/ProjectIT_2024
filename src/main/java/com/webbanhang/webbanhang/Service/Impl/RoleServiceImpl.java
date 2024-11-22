package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.RoleModel;
import com.webbanhang.webbanhang.Repository.IRoleRepository;
import com.webbanhang.webbanhang.Service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;

    @Override
    public List<RoleModel> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public RoleModel findRoleByID(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }
}
