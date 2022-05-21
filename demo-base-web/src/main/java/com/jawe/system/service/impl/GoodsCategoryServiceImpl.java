package com.jawe.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jawe.system.entity.GoodsCategory;
import com.jawe.system.mapper.GoodsCategoryMapper;
import com.jawe.system.service.GoodsCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jawe
 * @since 2021-10-28
 */
@Service
@Slf4j
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements GoodsCategoryService {

    @Override
    public List<GoodsCategory> getTreeList(boolean isEnable) {
        log.info(String.valueOf(isEnable));
        //获取第一层级
        QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", 0);
        wrapper.orderByAsc("sort");
        //是否舍弃禁用元素
        if (isEnable) {
            wrapper.eq("enable", 1);
        }
        List<GoodsCategory> parents = this.baseMapper.selectList(wrapper);
        //遍历第一层级，添加子节点
        if (parents != null && parents.size() > 0) {
            for (GoodsCategory goodsCategory :
                    parents) {
                this.findAllChild(goodsCategory, isEnable);
            };
        }
        return parents;
    }

    @Override
    public List<Integer> getArray(int categoryId) {
        GoodsCategory goodsCategory = this.baseMapper.selectById(categoryId);
        ArrayList<Integer> res = new ArrayList<>();
        res.add(goodsCategory.getPid());
        res.add(goodsCategory.getId());
        return res;
    }

    @Override
    public int isEnable(int id) {
        return this.baseMapper.isEnable(id);
    }

    //    @Override
//    public List<GoodsCategory> list() {
//        EntityWrapper<GoodsCategory> wrapper = new EntityWrapper<>();
//        wrapper.eq("parent_id", 0)
//                .or()
//                .isNull("parent_id")
//                .orderBy("sort");
//        // 得到一级节点资源列表
//        List<SysResource> resources = this.selectList(wrapper);
//        if (resources != null && resources.size() > 0) {
//            resources.forEach(this::findAllChild);
//        }
//        return resources;
//    }
//
//
    public void findAllChild(GoodsCategory resource, boolean isEnable) {
        QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", resource.getId());
        wrapper.orderByAsc("sort");
        //是否舍弃禁用元素
        if (isEnable) {
            wrapper.eq("enable", 1);
        }
        // 首次进入这个方法时，查出的是二级节点列表
        // 递归调用时，就能依次查出三、四、五等等级别的节点列表，
        // 递归能实现不同节点级别的无限调用,这个层次不易太深，否则有性能问题
        List<GoodsCategory> resources = this.baseMapper.selectList(wrapper);
        resource.setChildren(resources);
//        if (resources != null && resources.size() > 0) {
//            resources.forEach(this::findAllChild);
//        }
    }



}
