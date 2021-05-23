package com.lyh.admin.service;

import com.lyh.admin.dto.TreeDto;
import com.lyh.admin.pojo.TMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-21
 */
public interface ITMenuService extends IService<TMenu> {

    List<TreeDto> queryAllMenus(Integer roleId);

    Map<String, Object> menuList();

    void saveMenu(TMenu menu);

    TMenu findMenuByNameAndGrade(String menuName,Integer grade);

    TMenu findMenuByAclVaLUE(String aclValue);

    TMenu findMenuById(Integer id);
    TMenu findMenuByGradeAndUrl(String url,Integer grade);

    void updateMenu(TMenu menu);

    void deleteMenuById(Integer id);
}
