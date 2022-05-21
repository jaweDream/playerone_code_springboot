package com.jawe.system.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.jawe.handler.BusinessException;
import com.jawe.response.Result;
import com.jawe.response.ResultCode;
import com.jawe.system.entity.Department;
import com.jawe.system.service.DepartmentService;
import com.jawe.system.service.impl.DepartmentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jawe
 * @since 2021-05-26
 */
@Api(value = "学院（系）管理",tags = "学院信息")
@RestController
@CrossOrigin
@RequestMapping("/department")
public class DepartmentController {

//    @Autowired
//    private DepartmentService departmentService;
//
//    @ApiOperation(value = "查询学院及人数",notes = "分组查询学院及人数")
//    @GetMapping("/findDeptAndCount")
//    public Result findDeptAndCount(){
//        List<Department> departments = departmentService.findDeptAndCount();
//        if (departments.size()==0){
//            throw new BusinessException(ResultCode.DEPARTMENT_NOT_EXIST.getCode(),ResultCode.DEPARTMENT_NOT_EXIST.getMessage());
//        }else{
//            return Result.ok().data("departments",departments);
//        }
//
//    }
}

