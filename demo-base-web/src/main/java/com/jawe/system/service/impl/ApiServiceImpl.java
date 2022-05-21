package com.jawe.system.service.impl;

import com.jawe.system.entity.Api;
import com.jawe.system.mapper.ApiMapper;
import com.jawe.system.service.ApiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jawe
 * @since 2021-09-12
 */
@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {

}
