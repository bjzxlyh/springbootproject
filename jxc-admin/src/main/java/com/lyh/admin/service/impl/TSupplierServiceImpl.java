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
import com.lyh.admin.utils.PageResultUtil;
import org.springframework.stereotype.Service;

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
}
