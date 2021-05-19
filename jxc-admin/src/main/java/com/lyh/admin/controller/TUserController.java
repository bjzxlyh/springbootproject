package com.lyh.admin.controller;


import com.fasterxml.jackson.databind.Module;
import com.lyh.admin.exceptions.ParamsException;
import com.lyh.admin.model.RespBean;
import com.lyh.admin.pojo.TUser;
import com.lyh.admin.query.UserQuery;
import com.lyh.admin.service.ITUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.security.Principal;
import java.util.Map;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-10
 */

@RequestMapping("/user")
@Controller
public class TUserController {

    @Resource
    private ITUserService userService;

    /**
     * 登录界面
     * @param username
     * @param password
     * @param session
     * @return
     */
//    @RequestMapping("login")
//    @ResponseBody
//    public RespBean login(String username, String password, HttpSession session){
//
//        //try {
//            TUser user = userService.login(username,password);
//            session.setAttribute("user",user);
//            return RespBean.success("用户登录成功");
////        } catch (ParamsException e) {
////            e.printStackTrace();
////            return RespBean.error(e.getMsg());
////        }catch (Exception e){
////            e.printStackTrace();
////            return  RespBean.error("用户登录失败！");
////        }
//
//    }

    /**
     * 用户信息设置页面
     * @return
     */
    @RequestMapping("setting")
    public String setting(Principal principal, Model model){
        TUser user =userService.findTUserByUserName(principal.getName());
        model.addAttribute("user",user);
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
        //try {
            userService.updateUserInfo(user);
            return RespBean.success("用户信息更新成功!");
//        } catch (ParamsException e) {
//            e.printStackTrace();
//            return RespBean.error(e.getMsg());
//        }catch (Exception e){
//            e.printStackTrace();
//            return  RespBean.error("用户信息更新失败！");
//        }
    }

    /**
     * 密码修改更新页
     * @return
     */
    @RequestMapping("toPasswordPage")
    public String password(){
        return "user/password";
    }

    /**
     * 用户密码更新
     * @param principal
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
     @RequestMapping("updateUserPassword")
     @ResponseBody
     public RespBean updateUserPassword(Principal principal, String oldPassword, String newPassword, String confirmPassword){
         //try {
             userService.updateUserPassword(principal.getName(),oldPassword,newPassword,confirmPassword);
             return RespBean.success("密码修改成功!");
//         } catch (ParamsException e) {
//             e.printStackTrace();
//             return RespBean.error(e.getMsg());
//         }catch (Exception e){
//             e.printStackTrace();
//             return  RespBean.error("密码修改失败！");
//         }
     }

    /**
     * 用户管理主页
     * @return
     */
     @RequestMapping("index")
     public String index(){
         return "user/user";
     }

    @RequestMapping("list")
    @ResponseBody
     public Map<String,Object> userList(UserQuery userQuery){
         return userService.userList(userQuery);
     }
}
