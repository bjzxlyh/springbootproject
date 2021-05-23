package com.lyh.admin.controller;


import com.lyh.admin.dto.TreeDto;
import com.lyh.admin.model.RespBean;
import com.lyh.admin.pojo.TMenu;
import com.lyh.admin.service.ITMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-21
 */
@Controller
@RequestMapping("/menu")
public class TMenuController {

    @Resource
    private ITMenuService menuService;

    /**
     * 返回所有菜单的数据
     * @return
     */
    @RequestMapping("queryAllMenus")
    @ResponseBody
    public List<TreeDto> queryAllMenus(Integer roleId){
            return  menuService.queryAllMenus(roleId);
     }

    @RequestMapping("/index")
    public String index(){
        return "menu/menu";
    }

    /**
     * 菜单列表查询
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> menuList(){
        return menuService.menuList();
    }

    /**
     * 添加菜单页
     * @param grade
     * @param pId
     * @param model
     * @return
     */
    @RequestMapping("addMenuPage")
    public String addMenuPage(Integer grade, Integer pId, Model model){
        model.addAttribute("grade",grade);
        model.addAttribute("pId",pId);
        return "menu/add";
    }

    @RequestMapping("save")
    @ResponseBody
    public RespBean saveMenu(TMenu menu){
        menuService.saveMenu(menu);
        return RespBean.success("菜单记录添加成功！");
    }

    /**
     * 更新菜单页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("updateMenuPage")
    public String updateMenuPage(Integer id,Model model){
        model.addAttribute("menu",menuService.getById(id));
        return "menu/update";
    }

    @RequestMapping("update")
    @ResponseBody
    public RespBean updateMenu(TMenu menu){
        menuService.updateMenu(menu);
        return RespBean.success("菜单更新成功！");
    }
}
