package com.lyh.admin.service;

import com.lyh.admin.pojo.TGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.admin.query.GoodsQuery;

import java.util.Map;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-06-01
 */
public interface ITGoodsService extends IService<TGoods> {

    Map<String, Object> goodsList(GoodsQuery goodsQuery);
}
