package com.lyh.admin.service;

import com.lyh.admin.pojo.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-10
 */
public interface ITUserService extends IService<TUser> {

    /**
     *  用户登录方法
     * @param username
     * @param password
     * @param session
     * @return
     */


    TUser login(String username, String password);

    /**
     * 根据用户名查询用户记录
     * @param username
     * @return
     */
    public TUser findTUserByUserName(String username);



}
