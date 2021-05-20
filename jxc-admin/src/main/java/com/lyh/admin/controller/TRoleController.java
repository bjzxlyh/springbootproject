package com.lyh.admin.controller;


import com.lyh.admin.model.RespBean;
import com.lyh.admin.pojo.TRole;
import com.lyh.admin.pojo.TUser;
import com.lyh.admin.query.RoleQuery;
import com.lyh.admin.service.ITRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-19
 */
@Controller
@RequestMapping("/role")
public class TRoleController {

    @Resource
    private ITRoleService roleService;
    /**
     * 角色管理主页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "role/role";
    }

    /**
     * 角色列表查询
     * @param roleQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> roleList(RoleQuery roleQuery){
         return roleService.roleList(roleQuery);
    }

    /**
     * 添加|更新角色页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateRolePage")
    public String addOrUpdatePage(Integer id, Model model){
        if(null != id){
            model.addAttribute("role",roleService.getById(id));
        }
        return "role/add_update";
    }

    /**
     * 角色信息添加接口
     * @param user
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public RespBean saveUser(TRole role){
        roleService.saveRole(role);
        return RespBean.success("角色信息添加成功!");
    }

    /**
     * 角色信息更新接口
     * @param user
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public RespBean updateRole(TRole role){
        roleService.updateRole(role);
        return RespBean.success("角色记录更新成功!");
    }

    /**
     * 用户记录删除接口
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public  RespBean deleteRole(Integer id){
        roleService.deleteRole(id);
        return RespBean.success("角色信息删除成功！");
    }

    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleService.queryAllRoles(userId);
    }

    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId,Model model){
        model.addAttribute("roleId",roleId);
        return "role/grant";
    }
}
