package com.lyh.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lyh.admin.dto.TreeDto;
import com.lyh.admin.pojo.TMenu;
import com.lyh.admin.mapper.TMenuMapper;
import com.lyh.admin.service.ITMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.admin.service.ITRoleMenuService;
import com.lyh.admin.utils.AssertUtil;
import com.lyh.admin.utils.PageResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author 李毅恒
 * @since 2021-05-21
 */
@Service
public class TMenuServiceImpl extends ServiceImpl<TMenuMapper, TMenu> implements ITMenuService {

    /**
     * 展示菜单信息，以树的形式
     */
    @Resource
    private ITRoleMenuService roleMenuService;
    @Override
    public List<TreeDto> queryAllMenus(Integer roleId) {
         List<TreeDto> treeDtos = this.baseMapper.queryAllMenus();
         List<Integer> roleHasMenuIds = roleMenuService.queryRoleHasAllMenusByRoleId(roleId);
         if (CollectionUtils.isNotEmpty(roleHasMenuIds)){
             treeDtos.forEach(treeDto -> {
                 if (roleHasMenuIds.contains(treeDto.getId())){
                     treeDto.setChecked(true);
                 }
             });
         }
         return  treeDtos;
    }

    @Override
    public Map<String, Object> menuList() {
        List<TMenu> menus = this.list(new QueryWrapper<TMenu>().eq("is_del",0));
        return PageResultUtil.getResult((long)menus.size(),menus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void saveMenu(TMenu menu) {
        /**
         * 1.参数校验
         *      菜单名 不能为空
         *      菜单层级只支持三级
         * 2.同一层级下
         *      菜单名不可重复
         * 3.权限码
         *      全局唯一
         *      非空
         * 4.上级菜单
         *      上级菜单必须存在
         * 5.url判断
         *      菜单属于二级菜单 url 不可重复
         * 6.设置参数
         *      是否删除
         */

        AssertUtil.isTrue(StringUtils.isBlank(menu.getName()),"请输入菜单名！");
        Integer grade = menu.getGrade();
        AssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 || grade == 2),"菜单层级不合法！");
        AssertUtil.isTrue(null != this.findMenuByNameAndGrade(menu.getName(),menu.getGrade()),"该层级下菜单已存在！");
        AssertUtil.isTrue(null != this.findMenuByAclVaLUE(menu.getAclValue()),"权限码已存在！");
        AssertUtil.isTrue(null == menu.getPId() || null == this.findMenuById(menu.getPId()),"请指定上级菜单！");
        if (grade == 1){
            AssertUtil.isTrue(null != this.findMenuByGradeAndUrl(menu.getUrl(),menu.getGrade()),"该层级下url不可重复！");
        }

        menu.setIsDel(0);
        menu.setState(0);
        AssertUtil.isTrue(!(this.save(menu)),"菜单添加失败！");
    }

    @Override
    public TMenu findMenuByNameAndGrade(String menuName,Integer grade) {
        return this.getOne(new QueryWrapper<TMenu>().eq("is_del",0).eq("name",menuName).eq("grade",grade));
    }

    @Override
    public TMenu findMenuByAclVaLUE(String aclValue) {
        return this.getOne(new QueryWrapper<TMenu>().eq("is_del",0).eq("acl_value",aclValue));
    }

    @Override
    public TMenu findMenuById(Integer id) {
        return this.getOne(new QueryWrapper<TMenu>().eq("is_del",0).eq("id",id));
    }

    @Override
    public TMenu findMenuByGradeAndUrl(String url, Integer grade) {
        return this.getOne(new QueryWrapper<TMenu>().eq("is_del",0).eq("url",url).eq("grade",grade));
    }


}
