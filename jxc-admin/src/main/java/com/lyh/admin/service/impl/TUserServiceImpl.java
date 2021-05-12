package com.lyh.admin.service.impl;

import com.lyh.admin.pojo.TUser;
import com.lyh.admin.mapper.TUserMapper;
import com.lyh.admin.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.admin.utils.AssertUtil;
import com.lyh.admin.utils.StringUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-10
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

    @Override
    public TUser login(String username, String password) {
        AssertUtil.isTrue(StringUtil.isEmpty(username),"用户名不能为空!");
        AssertUtil.isTrue(StringUtil.isEmpty(password),"密码不能为空!");
        return null;
    }

    @Override
    public TUser findTUserByUserName(String username) {
        return null;
    }
}
