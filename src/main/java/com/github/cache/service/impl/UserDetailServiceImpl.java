package com.github.cache.service.impl;

import com.github.cache.pojo.doo.UserDetailDO;
import com.github.cache.repository.IUserDetailRepository;
import com.github.cache.service.IUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
import org.springframework.transaction.annotation.Transactional;

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
@CacheConfig(cacheNames = "user_cache")
public class UserDetailServiceImpl implements IUserDetailService {

    @Resource
    private IUserDetailRepository repository;

    /**
     * 指定了 cacheManager = "JsonCacheManager", 所以会用在 Redis 中生成 JSON 格式的字符串
     *
     * @param name 请求的用户名
     * @return UserDetailDO
     */
    @Cacheable(
            keyGenerator = "DefaultGenerator",
            condition = "#name !='name10'",
            unless = "#name.length() <= 4",
            cacheManager = "JsonCacheManager"
    )
    @Override
    public UserDetailDO findByName(String name) {
        log.info("---------- UserDetailService|findByName|{}", name);
        return repository.findByNameEquals(name);
    }

    /**
     * 几个属性:
     * 1.cacheNames/value: 指定缓存组件的名字;
     * 2.key: 缓存数据视同的 key, 可以用它来指定,默认是使用方法的参数, 1-方法的返回值,支持编写 SpEL
     * 3.keyGenerator:key 的生成器;可以指定 key 的生产器的组件 id, key/keyGenerator:二选一使用.
     * 将方法的运行结果进行缓存, 以后再要相同的数据, 直接冲缓存中获取, 不同调用方法;
     * 4.CacheManager: 管理多个 Cache 组件, 对缓存的正常的 CRUD 操作在 Cache 组件中, 每个缓存组件中有自己唯一的名字;
     * 5.condition: 指定符合条件的情况下才缓存 condition = '#id > 0'
     * 6.cacheResolver:
     * 7.unless: 否定缓存:当unless 指定的条件为 true ,方法的返回值就不会被缓存,可以获取带结果进行判断, unless = "#result == null"
     * 8.sync: 是否使用异步缓存
     * <p>
     * 原理:
     * 1.自动配置类:{@link org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration}
     * 2.支持的缓存配置类
     * 0 = "{org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration}"
     * 1 = "{org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration}"
     * 2 = "{org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration}"
     * 3 = "{org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration}"
     * 4 = "{org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration}"
     * 5 = "{org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration}"
     * 6 = "{org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration}"
     * 7 = "{org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration}"
     * 8 = "{org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration}"[默认]
     * 9 = "{org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration}"
     * <p>
     * 运行流程
     * <p>
     * - @Cacheable:
     * 1.方法运行事前, 先去查询 Cache(缓存组件),按照 cacheNames 指定的名字获取:
     * (CacheManager 先获取相应的缓存),第一次获取缓存如果没有 Cache 组件会自动创建.
     * 2.去 cache 中查找缓存的内容,使用一个 key ,默认就是方法的参数:
     * key 是按照某种策略生成的,默认使用的 keyGenerator生成的,默认使用 SimpleKeyGenerator 生成 key
     * SimpleKeyGenerator 生成 key 的默认策略:
     * 如果没有参数, key = new SimpleKey()
     * 如果只有一个参数, key = 参数的值
     * 如果有多个参数, key = new SimpleKey(params)
     * <p>
     * 3.没有查询到缓存就调用目标方法:
     * 4.将目标方法返回的结果放进缓存中.
     * -@Cacheable 标注的方法执行之前先检查缓存中有没有这个数据, 默认按照参数的值作为 key 去查询库,如果没有就运行方法将结果放入缓存中;
     *
     * @param phone 用户手机号码
     * @return UserDetailDO
     */
    @Override
    @Cacheable(keyGenerator = "DefaultGenerator")
    public UserDetailDO findByPhone(String phone) {
        log.info("---------- UserDetailService|findByPhone|{}", phone);
        return repository.findUserCacheDOByPhoneEquals(phone);
    }

    @Cacheable(keyGenerator = "DefaultGenerator")
    @Override
    public Page<UserDetailDO> findAll(@NotNull Pageable pageable) {
        log.info("---------- UserDetailService|findByPhone|{}", pageable.toString());
        return repository.findAll(pageable);
    }

    /**
     * -@Caching 复杂缓存配置
     *
     * @param userCacheDTO UserDetailDO
     * @return UserDetailDO
     */
    @Caching(
            cacheable = {
                    @Cacheable(key = "'[' + #a0.name + ']' "),
            },
            put = {
                    @CachePut(key = "'[' + #result.name + ']'"),
                    @CachePut(key = "'[' + #result.phone + ']'")
            }
    )
    @Override
    public UserDetailDO create(@NotNull UserDetailDO userCacheDTO) {
        log.info("UserDetailService|create|{}", userCacheDTO.toString());
        return repository.save(userCacheDTO);
    }

    /**
     * -@CachePut:既调用方法,又更新缓存: 修改了数据库的某个数据, 同步更新缓存
     * 运行时机:
     * 1.先调用目标方法;
     * 2.将目标方法的结果缓存起来;
     *
     * @param userCacheDTO 用户信息
     * @return UserDetailDO
     */
    @Override
    @CachePut(key = "'[' + #result.name + ']'")
    public UserDetailDO update(@NotNull UserDetailDO userCacheDTO) {
        log.info("---------- UserDetailService|update|{}", userCacheDTO.toString());
        return repository.save(userCacheDTO);
    }

    /**
     * -@CacheEvict 清除缓存
     * key:指定要清除的数据
     * allEntries = true:指定清除这个缓存中所有的数据
     * beforeInvocation = false: 缓存的清除是否在方法之前执行
     * 默认代表缓存清除操作是在方法执行之后执行:如果出现异常缓存就不会清除
     * beforeInvocation = false: 缓存的清除是否在方法之后执行
     * 无论方法是否出现异常,缓存都清除
     *
     * @param name name
     * @return ResponseEntity
     */
    @Override
    @CacheEvict(key = "'[' + #a0 + ']'")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Void> delete(String name) {
        log.info("---------- UserDetailService|delete|{}", name);
        repository.deleteUserCacheDOByNameIs(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @CacheEvict(allEntries = true)
    public ResponseEntity<Void> deleteAll() {
        log.info("---------- UserDetailService|deleteAll");
        repository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
