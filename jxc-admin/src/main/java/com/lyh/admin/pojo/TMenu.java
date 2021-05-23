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
 * 菜单表
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TMenu对象", description="菜单表")
@TableName("t_menu")
public class TMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "节点类型")
    private Integer state;

    @ApiModelProperty(value = "菜单url")
    private String url;

    @ApiModelProperty(value = "上级菜单id")
    private Integer pId;

    @ApiModelProperty(value = "权限码")
    private String aclValue;

    @ApiModelProperty(value = "菜单层级")
    private Integer grade;

    @ApiModelProperty(value = "是否删除")
    private Integer isDel;


}
