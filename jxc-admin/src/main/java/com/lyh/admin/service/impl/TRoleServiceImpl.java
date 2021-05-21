package com.lyh.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyh.admin.pojo.TRole;
import com.lyh.admin.mapper.TRoleMapper;
import com.lyh.admin.pojo.TRoleMenu;
import com.lyh.admin.pojo.TUser;
import com.lyh.admin.query.RoleQuery;
import com.lyh.admin.service.ITRoleMenuService;
import com.lyh.admin.service.ITRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.admin.utils.AssertUtil;
import com.lyh.admin.utils.PageResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-19
 */
@Service
public class TRoleServiceImpl extends ServiceImpl<TRoleMapper, TRole> implements ITRoleService {

    @Resource
    private ITRoleMenuService roleMenuService;

    /**
     * 查找角色名
     * @param roleName
     * @return
     */
    @Override
    public TRole findRoleByRoleName(String roleName) {
        return this.baseMapper.selectOne(new QueryWrapper<TRole>().eq("is_del",0).eq("name",roleName));
    }

    /**
     * 分页查询角色
     * @param roleQuery
     * @return
     */
    @Override
    public Map<String, Object> roleList(RoleQuery roleQuery) {
        IPage<TRole> page = new Page<TRole>(roleQuery.getPage(),roleQuery.getLimit());
        QueryWrapper<TRole> queryWrapper = new QueryWrapper<TRole>();
        queryWrapper.eq("is_del",0);
        if(StringUtils.isNotBlank(roleQuery.getRoleName())){
            queryWrapper.like("name",roleQuery.getRoleName());
        }

        page = this.baseMapper.selectPage(page,queryWrapper);
        return PageResultUtil.getResult(page.getTotal(),page.getRecords());
    }

    /**
     * 添加角色
     * @param role
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void saveRole(TRole role) {
        AssertUtil.isTrue(StringUtils.isBlank(role.getName()),"请输入角色名!");
        AssertUtil.isTrue(null != this.findRoleByRoleName(role.getName()),"角色名已存在！");
        role.setIsDel(0);
        AssertUtil.isTrue(!(this.save(role)),"角色添加失败!");
    }

    /**
     * 更新角色
     * @param role
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateRole(TRole role) {
        AssertUtil.isTrue(StringUtils.isBlank(role.getName()),"请输入角色名!");
        TRole temp = this.findRoleByRoleName(role.getName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(role.getId())),"角色名已存在！");
        AssertUtil.isTrue(!(this.updateById(role)),"角色更新失败!");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteRole(Integer id) {
        AssertUtil.isTrue(null == id,"请选择待删除的角色!");
        TRole role = this.getById(id);
        AssertUtil.isTrue(null == role,"待删除的角色不存在!");
        role.setIsDel(1);
        AssertUtil.isTrue(!(this.updateById(role)),"角色信息删除失败！");
    }

    @Override
    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        return this.baseMapper.queryAllRoles(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void addGrant(Integer roleId, Integer[] mids) {
        /**
         * 1.参数校验
         *    roleId 非空
         * 2.授权
         *   2.1 第一次授权
         *     批量添加即可
         *   2.2 第2+次 授权
         *     如果存在原始权限，此时删除原始权限，添加新的权限
         *     如果不存在，直接批量添加即可
         */

        TRole role = this.getById(roleId);
        AssertUtil.isTrue(null == role,"待授权的角色记录不存在！");
        int count = roleMenuService.count(new QueryWrapper<TRoleMenu>().eq("role_id",roleId));
        if (count > 0){
            AssertUtil.isTrue(!(roleMenuService.remove(new QueryWrapper<TRoleMenu>().eq("role_id",roleId))),"角色授权失败！");
        }

        if (null != mids && mids.length > 0){
            List<TRoleMenu> roleMenus = new ArrayList<TRoleMenu>();
            for (Integer mid : mids) {
                TRoleMenu roleMenu = new TRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(mid);
                roleMenus.add(roleMenu);
            }
            AssertUtil.isTrue(!(roleMenuService.saveBatch(roleMenus)),"角色授权失败！");
        }
    }
}
