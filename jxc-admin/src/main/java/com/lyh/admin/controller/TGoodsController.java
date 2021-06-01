package com.lyh.admin.controller;


import com.lyh.admin.pojo.TGoods;
import com.lyh.admin.query.GoodsQuery;
import com.lyh.admin.service.ITGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author 李毅恒
 * @since 2021-06-01
 */
@Controller
@RequestMapping("/goods")
public class TGoodsController {

    @Resource
    private ITGoodsService goodsService;

    @RequestMapping("index")
    public String index(){
        return "goods/goods";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> goodsList(GoodsQuery goodsQuery){
        return goodsService.goodsList(goodsQuery);
    }

}
