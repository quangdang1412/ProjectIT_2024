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
        return (List<RoleModel>) roleRepository.findAll();
    }

    @Override
    public RoleModel findRoleByID(String id) {
        return roleRepository.findById(Integer.valueOf(id)).orElseThrow(()-> new ResourceNotFoundException("Role not found"));
    }
}
