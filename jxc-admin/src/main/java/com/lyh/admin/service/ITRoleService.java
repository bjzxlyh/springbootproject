package com.lyh.admin.service;

import com.lyh.admin.pojo.TRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.admin.query.RoleQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-19
 */
public interface ITRoleService extends IService<TRole> {

    Map<String, Object> roleList(RoleQuery roleQuery);

    void saveRole(TRole role);

    TRole findRoleByRoleName(String roleName);

    void updateRole(TRole role);

    void deleteRole(Integer id);

    List<Map<String, Object>> queryAllRoles(Integer userId);

    void addGrant(Integer roleId, Integer[] mids);
}
