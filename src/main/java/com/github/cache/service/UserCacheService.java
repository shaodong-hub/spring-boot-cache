package com.github.cache.service;

import com.github.cache.pojo.UserCacheDO;
import com.github.cache.repository.IUserCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 创建时间为 18:52 2019-07-07
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "redis_cache")
public class UserCacheService {

    @Resource
    private IUserCacheRepository repository;

    @Cacheable(keyGenerator = "DefaultGenerator")
    public UserCacheDO findByName(String name) {
        log.info("UserCacheServiceImpl|findByName|{}", name);
        return repository.findUserCacheDOByNameIs(name);
    }

    @Cacheable(keyGenerator = "DefaultGenerator")
    public UserCacheDO findByPhone(String phone) {
        return repository.findUserCacheDOByPhoneEquals(phone);
    }


    /**
     * @param pageable 分页信息
     * @return Page
     */
    public Page<UserCacheDO> findAll(Pageable pageable) {
        return null;
    }

    /**
     * -@CachePut:既调用方法,又更新缓存: 修改了数据库的某个数据, 同步更新缓存
     * 运行时机:
     * 1.先调用目标方法;
     * 2.将目标方法的结果缓存起来;
     *
     * @param userCacheDTO
     * @return
     */

    @Caching(
            cacheable = {
                    @Cacheable(key = "#a0.name")
            },
            put = {
                    @CachePut(key = "#result.name"),
                    @CachePut(key = "#result.phone")
            }
    )
    public UserCacheDO create(UserCacheDO userCacheDTO) {
        return repository.save(userCacheDTO);
    }

    @CachePut(key = "#result.name")
    public UserCacheDO update(UserCacheDO userCacheDTO) {
        return repository.save(userCacheDTO);
    }

    /**
     * -@CacheEvict
     *
     * @param name name
     */
    @CacheEvict
    public ResponseEntity<Void> delete(String name) {
        repository.deleteUserCacheDOByNameIs(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
