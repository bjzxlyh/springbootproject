package com.lyh.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyh.admin.pojo.TCustomer;
import com.lyh.admin.mapper.TCustomerMapper;
import com.lyh.admin.pojo.TSupplier;
import com.lyh.admin.pojo.TUserRole;
import com.lyh.admin.query.CustomerQuery;
import com.lyh.admin.service.ITCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.admin.utils.AssertUtil;
import com.lyh.admin.utils.PageResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-06-01
 */
@Service
public class TCustomerServiceImpl extends ServiceImpl<TCustomerMapper, TCustomer> implements ITCustomerService {


    @Override
    public Map<String, Object> customerList(CustomerQuery customerQuery) {
        IPage<TCustomer> page = new Page<>(customerQuery.getPage(),customerQuery.getLimit());
        QueryWrapper<TCustomer> queryWrapper = new QueryWrapper<TCustomer>();
        if (StringUtils.isNotBlank(customerQuery.getCustomerName())){
            queryWrapper.like("name",customerQuery.getCustomerName());
        }
        page = this.baseMapper.selectPage(page,queryWrapper);
        return PageResultUtil.getResult(page.getTotal(),page.getRecords());

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void saveCustomer(TCustomer customer) {
        checkParams(customer.getName(),customer.getContact(),customer.getNumber());
        AssertUtil.isTrue(null != this.findCustomerByName(customer.getName()),"客户已存在！");
        customer.setIsDel(0);
        AssertUtil.isTrue(!(this.save(customer)),"记录添加失败！");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateCustomer(TCustomer customer) {
        AssertUtil.isTrue(null == this.getById(customer.getId()),"请选择客户记录！");
        checkParams(customer.getName(),customer.getContact(),customer.getNumber());
        TCustomer temp = this.findCustomerByName(customer.getName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(customer.getId())),"客户已存在！");
        AssertUtil.isTrue(!(this.updateById(customer)),"记录更新失败！");
    }

    @Override
    public TCustomer findCustomerByName(String name) {
        return this.getOne(new QueryWrapper<TCustomer>().eq("is_del",0).eq("name",name));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteCustomer(Integer[] ids) {
        AssertUtil.isTrue(null == ids || ids.length == 0,"请选择待删除的id");
        List<TCustomer> supplierList = new ArrayList<TCustomer>();
        for (Integer id : ids) {
            TCustomer temp = this.getById(id);
            temp.setIsDel(1);
            supplierList.add(temp);
        }
        AssertUtil.isTrue(!(this.updateBatchById(supplierList)),"记录删除失败！");
    }

    private void checkParams(String name,String contact,String number){
        AssertUtil.isTrue(StringUtils.isBlank(name),"请输入客户名称!");
        AssertUtil.isTrue(StringUtils.isBlank(contact),"请输入联系人!");
        AssertUtil.isTrue(StringUtils.isBlank(number),"请输入联系电话!");
    }
}
