package com.lyh.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyh.admin.pojo.TRole;
import com.lyh.admin.mapper.TRoleMapper;
import com.lyh.admin.pojo.TUser;
import com.lyh.admin.query.RoleQuery;
import com.lyh.admin.service.ITRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.admin.utils.PageResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
}
