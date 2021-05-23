package com.lyh.admin.service.impl;

import com.lyh.admin.pojo.TRoleMenu;
import com.lyh.admin.mapper.TRoleMenuMapper;
import com.lyh.admin.service.ITRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.admin.service.ITUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-21
 */
@Service
public class TRoleMenuServiceImpl extends ServiceImpl<TRoleMenuMapper, TRoleMenu> implements ITRoleMenuService {


    @Override
    public List<Integer> queryRoleHasAllMenusByRoleId(Integer roleId) {

        return this.baseMapper.queryRoleHasAllMenusByRoleId(roleId);
    }

    @Override
    public List<String> findAuthoritiesByRoleName(List<String> roleNames) {
        return this.baseMapper.findAuthoritiesByRoleName(roleNames);
    }
}
