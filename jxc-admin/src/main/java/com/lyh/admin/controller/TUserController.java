package com.lyh.admin.controller;


import com.lyh.admin.exceptions.ParamsException;
import com.lyh.admin.model.RespBean;
import com.lyh.admin.pojo.TUser;
import com.lyh.admin.service.ITUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-10
 */

@RequestMapping("/t-user")
@Controller
public class TUserController {

    @Resource
    private ITUserService userService;

    @RequestMapping("login")
    @ResponseBody
    public RespBean login(String username, String password, HttpSession session){

        try {
            TUser user = userService.login(username,password);
            session.setAttribute("user",user);
            return RespBean.success("用户登录成功");
        } catch (ParamsException e) {
            e.printStackTrace();
            return RespBean.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return  RespBean.error("用户登录失败！");
        }

    }

    /**
     * 用户信息设置页面
     * @return
     */
    @RequestMapping("setting")
    public String setting(HttpSession session){
        TUser user =(TUser) session.getAttribute("user");
        session.setAttribute("user",userService.getById(user.getId()));
        return "user/setting";
    }

    /**
     * 用户更新页面
     * @param user
     * @return
     */
    @RequestMapping("updateUserInfo")
    @ResponseBody
    public RespBean updateUserInfo(TUser user){
        try {
            userService.updateUserInfo(user);
            return RespBean.success("用户信息更新成功!");
        } catch (ParamsException e) {
            e.printStackTrace();
            return RespBean.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return  RespBean.error("用户信息更新失败！");
        }
    }

}
