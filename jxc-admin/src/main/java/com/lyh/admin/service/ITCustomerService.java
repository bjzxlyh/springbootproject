package com.lyh.admin.service;

import com.lyh.admin.pojo.TCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.admin.query.CustomerQuery;

import java.util.Map;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-06-01
 */
public interface ITCustomerService extends IService<TCustomer> {

    Map<String, Object> customerList(CustomerQuery customerQuery);

    void saveCustomer(TCustomer customer);

    void updateCustomer(TCustomer customer);

    TCustomer findCustomerByName(String name);

    void deleteCustomer(Integer[] ids);
}
