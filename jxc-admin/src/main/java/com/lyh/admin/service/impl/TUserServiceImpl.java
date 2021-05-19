package com.lyh.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyh.admin.pojo.TUser;
import com.lyh.admin.mapper.TUserMapper;
import com.lyh.admin.query.UserQuery;
import com.lyh.admin.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.admin.utils.AssertUtil;
import com.lyh.admin.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
//    @Override
//    public TUser login(String username, String password) {
//        AssertUtil.isTrue(StringUtil.isEmpty(username),"用户名不能为空!");
//        AssertUtil.isTrue(StringUtil.isEmpty(password),"密码不能为空!");
//        TUser Tuser = this.findTUserByUserName(username);
//        AssertUtil.isTrue(null==Tuser,"该用户记录不存在或已注销！");
//        AssertUtil.isTrue(!(Tuser.getPassword().equals(password)),"密码错误！");
//        return Tuser;
//    }

    /**
     * findTUserByUserName
     * @param username
     * @return
     */
    @Override
    public TUser findTUserByUserName(String username) {
        return this.baseMapper.selectOne(new QueryWrapper<TUser>().eq("is_del",0).eq("user_name",username));
    }

    /**
     * 更新用户信息
     * @param user
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateUserInfo(TUser user) {
        AssertUtil.isTrue(StringUtil.isEmpty(user.getUsername()),"用户名不为空!");
        TUser temp = this.findTUserByUserName(user.getUsername());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())),"用户名已存在!");
        AssertUtil.isTrue(!(this.updateById(user)),"用户信息更新失败！");

    }

    /**
     * 修改密码
     * @param userName
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateUserPassword(String userName, String oldPassword, String newPassword, String confirmPassword) {
        TUser user = null;
        user = this.findTUserByUserName(userName);
        AssertUtil.isTrue(null == user,"用户名不存在或未登录!");
        AssertUtil.isTrue(StringUtil.isEmpty(oldPassword),"请输入原始密码!");
        AssertUtil.isTrue(StringUtil.isEmpty(newPassword),"请输入新密码!");
        AssertUtil.isTrue(StringUtil.isEmpty(confirmPassword),"请输入确认密码!");
        AssertUtil.isTrue(!(passwordEncoder.matches(oldPassword,user.getPassword())),"原始密码输入错误!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"密码输入不一致!");
        AssertUtil.isTrue(newPassword.equals(oldPassword),"新密码与原密码不能一致!");
        user.setPassword(passwordEncoder.encode(newPassword));
        AssertUtil.isTrue(!(this.updateById(user)),"用户密码更新失败!");
    }

    /**
     * 用户管理查询
     * @param userQuery
     * @return
     */
    @Override
    public Map<String, Object> userList(UserQuery userQuery) {
        IPage<TUser> page = new Page<TUser>(userQuery.getPage(),userQuery.getLimit());
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<TUser>();
        queryWrapper.eq("is_del",0);
        if(StringUtils.isNotBlank(userQuery.getUserName())){
            queryWrapper.like("user_name",userQuery.getUserName());
        }

        page = this.baseMapper.selectPage(page,queryWrapper);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",0);
        map.put("msg","");
        map.put("data",page.getRecords());
        map.put("count",page.getTotal());
        return map;
    }

    /**
     * 用户添加操作
     * @param user
     */
    @Override
    public void saveUser(TUser user) {
        /**
         * 用户名
         *    非空  不可重复
         * 用户密码默认设置123456,后续自己改
         * 用户默认有效
         */

        AssertUtil.isTrue(StringUtils.isBlank(user.getUsername()),"用户名不能为空！");
        AssertUtil.isTrue(null != this.findTUserByUserName(user.getUsername()),"用户名已存在！");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setIsDel(0);
        AssertUtil.isTrue(!(this.save(user)),"用户信息添加失败!");
    }

    /**
     * 用户更新操作
     * @param user
     */
    @Override
    public void updateUser(TUser user) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getUsername()),"用户名不能为空！");
        TUser temp = this.findTUserByUserName(user.getUsername());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())),"用户名已存在！");
        AssertUtil.isTrue(!(this.updateById(user)),"用户信息更新成功！");
    }
}
