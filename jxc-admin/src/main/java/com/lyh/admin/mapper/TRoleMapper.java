package com.lyh.admin.mapper;

import com.lyh.admin.pojo.TRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-19
 */
public interface TRoleMapper extends BaseMapper<TRole> {

    List<Map<String, Object>> queryAllRoles(Integer userId);
}
