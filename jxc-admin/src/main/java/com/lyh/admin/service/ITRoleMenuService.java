package com.lyh.admin.service;

import com.lyh.admin.pojo.TRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-21
 */
public interface ITRoleMenuService extends IService<TRoleMenu> {

    List<Integer> queryRoleHasAllMenusByRoleId(Integer roleId);

    List<String> findAuthoritiesByRoleName(List<String> roleNames);
}
