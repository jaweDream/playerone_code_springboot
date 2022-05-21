package com.jawe.system.service.impl;

import com.jawe.system.entity.Address;
import com.jawe.system.mapper.AddressMapper;
import com.jawe.system.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jawe
 * @since 2021-11-20
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

}
