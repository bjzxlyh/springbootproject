package com.lyh.admin.service.impl;

import com.lyh.admin.pojo.TUserRole;
import com.lyh.admin.mapper.TUserRoleMapper;
import com.lyh.admin.service.ITUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-20
 */
@Service
public class TUserRoleServiceImpl extends ServiceImpl<TUserRoleMapper, TUserRole> implements ITUserRoleService {
    @Override
    public List<String> findRolesByUserName(String userName) {
        return this.baseMapper.findRolesByUserName(userName);
    }
}
