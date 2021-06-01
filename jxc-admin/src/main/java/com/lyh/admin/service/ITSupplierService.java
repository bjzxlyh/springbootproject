package com.lyh.admin.service;

import com.lyh.admin.pojo.TSupplier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.admin.query.SupplierQuery;

import java.util.Map;

/**
 * <p>
 * 供应商表 服务类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-23
 */
public interface ITSupplierService extends IService<TSupplier> {

    Map<String, Object> supplierList(SupplierQuery supplierQuery);
    TSupplier findSupplierByName(String name);

    void saveSupplier(TSupplier supplier);

    void updateSupplier(TSupplier supplier);

    void deleteSupplier(Integer[] ids);
}
