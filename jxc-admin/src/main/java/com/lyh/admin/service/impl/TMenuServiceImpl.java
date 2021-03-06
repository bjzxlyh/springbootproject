package com.lyh.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lyh.admin.dto.TreeDto;
import com.lyh.admin.pojo.TMenu;
import com.lyh.admin.mapper.TMenuMapper;
import com.lyh.admin.pojo.TRoleMenu;
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateMenu(TMenu menu) {
        /**
         * 1.参数校验
         *      菜单名 不能为空
         *      待更新记录必须存在
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
         */
        AssertUtil.isTrue(null == menu.getId() || null == this.findMenuById(menu.getId()),"待更新的记录不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(menu.getName()),"菜单名不能为空!");
        Integer grade = menu.getGrade();
        AssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 || grade == 2),"菜单层级不合法！");
        TMenu temp = this.findMenuByNameAndGrade(menu.getName(),menu.getGrade());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(menu.getId())),"该层级下菜单已存在!");
        temp = this.findMenuByAclVaLUE(menu.getAclValue());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(menu.getId())),"权限码已存在！");
        AssertUtil.isTrue(null == menu.getPId() || null == this.findMenuById(menu.getPId()),"请指定上级菜单！");
        if (grade == 1){
            temp = this.findMenuByGradeAndUrl(menu.getUrl(),menu.getGrade());
            AssertUtil.isTrue(null != temp && !(temp.getId().equals(menu.getId())),"该层级下url不可重复！");
        }

        AssertUtil.isTrue(!(this.updateById(menu)),"菜单记录更新失败！");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteMenuById(Integer id) {
        /**
         * 1.参数校验
         *  记录必须存在
         * 2.子菜单
         *  如果存在子菜单 不允许直接删除上级菜单
         * 3.角色菜单表关联
         *  将关联的角色菜单记录一并删除
         * 4.执行菜单记录删除
         */
        TMenu menu = this.findMenuById(id);
        AssertUtil.isTrue(null == id || null == this.findMenuById(id),"待删除记录不存在!");
        int count = this.count(new QueryWrapper<TMenu>().eq("is_del",0).eq("p_id",id));
        AssertUtil.isTrue(count > 0,"存在子菜单，不允许删除!");
        count = roleMenuService.count(new QueryWrapper<TRoleMenu>().eq("menu_id",id));
        if(count > 0){
            AssertUtil.isTrue(!(roleMenuService.remove(new QueryWrapper<TRoleMenu>().eq("menu_id",id))),"菜单删除失败！");
        }
        menu.setIsDel(1);
        AssertUtil.isTrue(!(this.updateById(menu)),"菜单删除失败！");
    }


}
