package com.lyh.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyh.admin.pojo.TSupplier;
import com.lyh.admin.mapper.TSupplierMapper;
import com.lyh.admin.query.SupplierQuery;
import com.lyh.admin.service.ITSupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.admin.utils.AssertUtil;
import com.lyh.admin.utils.PageResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 供应商表 服务实现类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-23
 */
@Service
public class TSupplierServiceImpl extends ServiceImpl<TSupplierMapper, TSupplier> implements ITSupplierService {

    @Override
    public Map<String, Object> supplierList(SupplierQuery supplierQuery) {
        IPage<TSupplier> page = new Page<>(supplierQuery.getPage(),supplierQuery.getLimit());
        QueryWrapper<TSupplier> queryWrapper = new QueryWrapper<TSupplier>();
        if (StringUtils.isNotBlank(supplierQuery.getSupplierName())){
            queryWrapper.like("name",supplierQuery.getSupplierName());
        }
        page = this.baseMapper.selectPage(page,queryWrapper);
        return PageResultUtil.getResult(page.getTotal(),page.getRecords());
    }

    @Override
    public TSupplier findSupplierByName(String name) {
        return this.getOne(new QueryWrapper<TSupplier>().eq("is_del",0).eq("name",name));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void saveSupplier(TSupplier supplier) {
        /**
         * 供应商名称非空 联系人 联系电话
         * 名称不可重复
         * isDel 0
         *
         */
        checkParams(supplier.getName(),supplier.getContact(),supplier.getNumber());
        AssertUtil.isTrue(null != this.findSupplierByName(supplier.getName()),"供应商已存在！");
        supplier.setIsDel(0);
        AssertUtil.isTrue(!(this.save(supplier)),"记录添加失败！");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateSupplier(TSupplier supplier) {
        AssertUtil.isTrue(null == this.getById(supplier.getId()),"请选择供应商记录！");
        checkParams(supplier.getName(),supplier.getContact(),supplier.getNumber());
        TSupplier temp = this.findSupplierByName(supplier.getName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(supplier.getId())),"供应商已存在！");
        AssertUtil.isTrue(!(this.updateById(supplier)),"记录更新失败！");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteSupplier(Integer[] ids) {
        AssertUtil.isTrue(null == ids || ids.length == 0,"请选择待删除的id");
        List<TSupplier> supplierList = new ArrayList<TSupplier>();
        for (Integer id : ids) {
            TSupplier temp = this.getById(id);
            temp.setIsDel(1);
            supplierList.add(temp);
        }
        AssertUtil.isTrue(!(this.updateBatchById(supplierList)),"记录删除失败！");
    }

    /**
     * 检查供应商名称联系人 联系电话 非空
     * @param name
     * @param contact
     * @param number
     */
    private void checkParams(String name,String contact,String number){
        AssertUtil.isTrue(StringUtils.isBlank(name),"请输入供应商名称!");
        AssertUtil.isTrue(StringUtils.isBlank(contact),"请输入联系人!");
        AssertUtil.isTrue(StringUtils.isBlank(number),"请输入联系电话!");
    }
}
