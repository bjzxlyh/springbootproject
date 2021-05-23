package com.lyh.admin.mapper;

import com.lyh.admin.pojo.TRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色菜单表 Mapper 接口
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-21
 */
public interface TRoleMenuMapper extends BaseMapper<TRoleMenu> {
    List<Integer> queryRoleHasAllMenusByRoleId(Integer roleId);

    List<String>  findAuthoritiesByRoleName(List<String> roleNames);

}
