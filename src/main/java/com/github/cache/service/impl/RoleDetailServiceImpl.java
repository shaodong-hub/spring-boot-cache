package com.github.cache.service.impl;

import com.github.cache.pojo.doo.RoleDetailDO;
import com.github.cache.repository.IRoleDetailRepository;
import com.github.cache.service.IRoleDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 创建时间为 下午3:26 2019/11/27
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@Slf4j
@Service
@CacheConfig(cacheNames = "test_cache")
public class RoleDetailServiceImpl implements IRoleDetailService {

    @Resource
    private IRoleDetailRepository repository;

    @Cacheable(keyGenerator = "DefaultGenerator", cacheManager = "JsonCacheManager")
    @Override
    public RoleDetailDO findById(Long id) {
        log.info("findById");
        return repository.findById(id).orElseGet(RoleDetailDO::new);
    }

}
