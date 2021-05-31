package com.lyh.admin.controller;


import com.lyh.admin.model.RespBean;
import com.lyh.admin.pojo.TSupplier;
import com.lyh.admin.query.SupplierQuery;
import com.lyh.admin.query.UserQuery;
import com.lyh.admin.service.ITSupplierService;
import com.lyh.admin.service.ITUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 供应商表 前端控制器
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-23
 */
@Controller
@RequestMapping("/supplier")
public class TSupplierController {

    @Resource
    private ITSupplierService supplierService;

    @RequestMapping("index")
    public String index(){
        return "supplier/supplier";
    }


    /**
     * 供应商查询列表接口
     * @param supplierQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> supplierList(SupplierQuery supplierQuery){
        return supplierService.supplierList(supplierQuery);
    }

    @RequestMapping("addOrUpdateSupplierPage")
    public String addOrUpdateSupplierPage(Integer id, Model model){
        if(null != id){
            model.addAttribute("supplier",supplierService.getById(id));
        }
        return "supplier/add_update";
    }

    /**
     * 添加供应商
     * @param supplier
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public RespBean saveSupplier(TSupplier supplier){
        supplierService.saveSupplier(supplier);
        return RespBean.success("供应商记录添加成功！");
    }

    /**
     * 更新供应商
     * @param supplier
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public RespBean updateSupplier(TSupplier supplier){
        supplierService.updateSupplier(supplier);
        return RespBean.success("供应商记录更新成功！");
    }

}
