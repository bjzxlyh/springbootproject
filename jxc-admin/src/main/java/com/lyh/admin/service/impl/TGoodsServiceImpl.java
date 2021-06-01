package com.lyh.admin.service.impl;

import com.lyh.admin.pojo.TGoods;
import com.lyh.admin.mapper.TGoodsMapper;
import com.lyh.admin.query.GoodsQuery;
import com.lyh.admin.service.ITGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-06-01
 */
@Service
public class TGoodsServiceImpl extends ServiceImpl<TGoodsMapper, TGoods> implements ITGoodsService {

    @Override
    public Map<String, Object> goodsList(GoodsQuery goodsQuery) {
        return null;
    }
}
