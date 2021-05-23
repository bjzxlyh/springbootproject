package com.lyh.admin.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_role")
@ApiModel(value="TRole对象", description="角色表")
public class TRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "描述")
    private String remarks;

    @ApiModelProperty(value = "是否删除")
    private Integer isDel;


}
