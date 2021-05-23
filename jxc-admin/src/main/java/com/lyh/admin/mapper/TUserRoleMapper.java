package com.lyh.admin.mapper;

import com.lyh.admin.pojo.TUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-20
 */
public interface TUserRoleMapper extends BaseMapper<TUserRole> {

    List<String> findRolesByUserName(String userName);
}
