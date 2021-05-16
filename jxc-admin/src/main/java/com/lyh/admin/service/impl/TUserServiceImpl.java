package com.lyh.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyh.admin.pojo.TUser;
import com.lyh.admin.mapper.TUserMapper;
import com.lyh.admin.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.admin.utils.AssertUtil;
import com.lyh.admin.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        TUser Tuser = this.findTUserByUserName(username);
        AssertUtil.isTrue(null==Tuser,"该用户记录不存在或已注销！");
        AssertUtil.isTrue(!(Tuser.getPassword().equals(password)),"密码错误！");
        return Tuser;
    }

    @Override
    public TUser findTUserByUserName(String username) {
        return this.baseMapper.selectOne(new QueryWrapper<TUser>().eq("is_del",0).eq("user_name",username));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateUserInfo(TUser user) {
        AssertUtil.isTrue(StringUtil.isEmpty(user.getUserName()),"用户名不为空!");
        TUser temp = this.findTUserByUserName(user.getUserName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())),"用户名已存在!");
        AssertUtil.isTrue(!(this.updateById(user)),"用户信息更新失败！");

    }
}
