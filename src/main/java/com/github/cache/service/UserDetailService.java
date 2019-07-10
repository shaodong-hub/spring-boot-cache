package com.github.cache.service;

import com.github.cache.pojo.ReturnDO;
import com.github.cache.pojo.UserDetailDO;
import com.github.cache.repository.IUserDetailRepository;
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
public class UserDetailService {

    @Resource
    private IUserDetailRepository repository;

    /**
     * @param name 请求的用户名
     * @return UserDetailDO
     */
    @Cacheable(keyGenerator = "DefaultGenerator", condition = "#name!='name10'", unless = "#name.length()>4")
    public ReturnDO<UserDetailDO> findByName(String name) {
        log.info("UserDetailService|findByName|{}", name);
        UserDetailDO userDetailDO = repository.findUserCacheDOByNameIs(name);
        return ReturnDO.<UserDetailDO>builder().data(userDetailDO).build();
    }

    @Cacheable(keyGenerator = "DefaultGenerator")
    public ReturnDO<UserDetailDO> findByPhone(String phone) {
        log.info("UserDetailService|findByPhone|{}", phone);
        UserDetailDO userDetailDO = repository.findUserCacheDOByPhoneEquals(phone);
        return ReturnDO.<UserDetailDO>builder().data(userDetailDO).build();
    }

    /**
     * @param pageable 分页信息
     * @return Page
     */
    @Cacheable(keyGenerator = "DefaultGenerator")
    public Page<UserDetailDO> findAll(Pageable pageable) {
        log.info("UserDetailService|findByPhone|{}", pageable.toString());
        return repository.findAll(pageable);
    }

    /**
     * -@CachePut:既调用方法,又更新缓存: 修改了数据库的某个数据, 同步更新缓存
     * 运行时机:
     * 1.先调用目标方法;
     * 2.将目标方法的结果缓存起来;
     *
     * @param userCacheDTO UserDetailDO
     * @return UserDetailDO
     */
    @Caching(
            cacheable = {
                    @Cacheable(key = "#a0.name")
            },
            put = {
                    @CachePut(key = "#result.data.name"),
                    @CachePut(key = "#result.data.phone")
            }
    )
    public ReturnDO<UserDetailDO> create(UserDetailDO userCacheDTO) {
        UserDetailDO userDetailDO = repository.save(userCacheDTO);
        return ReturnDO.<UserDetailDO>builder().data(userDetailDO).build();
    }

    @CachePut(key = "#result.data.name")
    public ReturnDO<UserDetailDO> update(UserDetailDO userCacheDTO) {
        UserDetailDO userDetailDO = repository.save(userCacheDTO);
        return ReturnDO.<UserDetailDO>builder().data(userDetailDO).build();
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

    @CacheEvict(allEntries = true)
    public ResponseEntity<Void> deleteAll() {
        repository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
