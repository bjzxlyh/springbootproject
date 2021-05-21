package com.lyh.admin.controller;


import com.lyh.admin.dto.TreeDto;
import com.lyh.admin.service.ITMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    public List<TreeDto> queryAllMenus(){
            return  menuService.queryAllMenus();
     }
}
