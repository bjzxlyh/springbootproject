package com.lyh.admin.service.impl;

import com.lyh.admin.service.IRbacService;
import com.lyh.admin.service.ITRoleMenuService;
import com.lyh.admin.service.ITUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RbacServiceImpl implements IRbacService {
    @Resource
    private ITUserRoleService userRoleService;
    @Resource
    private ITRoleMenuService roleMenuService;
    @Override
    public List<String> findRolesByUserName(String userName) {
        return userRoleService.findRolesByUserName(userName);
    }

    @Override
    public List<String> findAuthoritiesByRoleName(List<String> roleNames) {
        return roleMenuService.findAuthoritiesByRoleName(roleNames);
    }
}
