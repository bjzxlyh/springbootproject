package com.lyh.admin.query;

import lombok.Data;

@Data
public class GoodsQuery extends BaseQuery{
    private String goodsName;
    private Integer typeId;
}
