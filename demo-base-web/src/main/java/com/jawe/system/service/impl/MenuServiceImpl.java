package com.jawe.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jawe.system.entity.Menu;
import com.jawe.system.mapper.MenuMapper;
import com.jawe.system.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jawe
 * @since 2021-09-12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Override
    public List<Menu> queryMenuTree() {
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Menu> menu_pid = menuQueryWrapper.orderByAsc("menu_pid","sort");
        List<Menu> allMenu = this.baseMapper.selectList(menu_pid);

        allMenu.remove(0);
        // 结果集合
        List<Menu> menus = new LinkedList<>();
        for (Menu menu :
                allMenu) {
            if (menu.getMenuPid() <= 1){
                menus.add(menu);
            }else{
                for (Menu menu2:menus
                     ) {
                    if (menu2.getId() == menu.getMenuPid()){
                        if (menu2.getChildren()==null){
                            ArrayList<Menu> menuChildren = new ArrayList<>();
                            menu2.setChildren(menuChildren);
                        }
                        menu2.getChildren().add(menu);
                    }
                }
            }
        }

        return menus;
    }


//    @Override
//    public List<Menu> queryMenuTree() {
//        Wrapper queryObj = new QueryWrapper<>().orderByAsc("level","sort");
//        List<Menu> allMenu = super.list(queryObj);
//        // 0L：表示根节点的父ID
//        List<Menu> resultList = transferMenuVo(allMenu, 0L);
//        return resultList;
//    }
//    /**
//     * 封装菜单视图
//     * @param allMenu
//     * @param parentId
//     * @return
//     */
//    private List<MenuVo> transferMenuVo(List<Menu> allMenu, Long parentId){
//        List<MenuVo> resultList = new ArrayList<>();
//        if(!CollectionUtils.isEmpty(allMenu)){
//            for (Menu source : allMenu) {
//                if(parentId.longValue() == source.getParentId().longValue()){
//                    MenuVo menuVo = new MenuVo();
//                    BeanUtils.copyProperties(source, menuVo);
//                    //递归查询子菜单，并封装信息
//                    List<MenuVo> childList = transferMenuVo(allMenu, source.getId());
//                    if(!CollectionUtils.isEmpty(childList)){
//                        menuVo.setChildMenu(childList);
//                    }
//                    resultList.add(menuVo);
//                }
//            }
//        }
//        return resultList;
//    }
}
