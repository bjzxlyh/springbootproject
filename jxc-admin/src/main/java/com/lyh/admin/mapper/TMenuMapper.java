package com.lyh.admin.mapper;

import com.lyh.admin.dto.TreeDto;
import com.lyh.admin.pojo.TMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-21
 */
public interface TMenuMapper extends BaseMapper<TMenu> {
    List<TreeDto> queryAllMenus();
}
