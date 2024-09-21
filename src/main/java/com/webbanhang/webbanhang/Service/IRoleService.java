package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.Model.RoleModel;

import java.util.List;

public interface IRoleService {
    List<RoleModel> getAllRole();
    RoleModel findRoleByID(String id);
}
