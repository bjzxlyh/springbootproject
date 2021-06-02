package com.lyh.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyh.admin.pojo.TGoods;
import com.lyh.admin.mapper.TGoodsMapper;
import com.lyh.admin.query.GoodsQuery;
import com.lyh.admin.service.ITGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.admin.utils.PageResultUtil;
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
        IPage<TGoods> page = new Page<TGoods>(goodsQuery.getPage(),goodsQuery.getLimit());
        page = this.baseMapper.queryGoodsByParams(page,goodsQuery);
        return PageResultUtil.getResult(page.getTotal(),page.getRecords());
    }
}
