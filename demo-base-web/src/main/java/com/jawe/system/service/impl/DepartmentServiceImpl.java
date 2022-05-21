package com.jawe.system.service.impl;

import com.jawe.system.entity.Department;
import com.jawe.system.mapper.DepartmentMapper;
import com.jawe.system.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jawe
 * @since 2021-05-26
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

//    @Override
//    public List<Department> findDeptAndCount() {
//        return this.baseMapper.findDeptAndCount();
//    }
}
