package com.lyh.admin.service;

import com.lyh.admin.pojo.TUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-20
 */
public interface ITUserRoleService extends IService<TUserRole> {

    List<String> findRolesByUserName(String userName);
}
