package com.github.cache.service;

import com.github.cache.pojo.UserSimpleDO;
import com.github.cache.repository.IUserSimpleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <p>
 * 创建时间为 21:43 2019-07-09
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
public class UserSimpleService {

    @Resource
    private IUserSimpleRepository repository;

    @Cacheable(keyGenerator = "DefaultGenerator")
    public UserSimpleDO getUserSimpleByName(String name) {
        log.info("从数据库中获取数据 getUserSimpleByName:{}", name);
        Optional<UserSimpleDO> optional = repository.findById(name);
        return optional.orElseGet(UserSimpleDO::new);
    }

    @CachePut(key = "#result.name")
    public UserSimpleDO createUserSimple(UserSimpleDO userSimpleDO) {
        return repository.save(userSimpleDO);
    }

    public UserSimpleDO updateUserSimple(UserSimpleDO userSimpleDO) {
        return repository.save(userSimpleDO);
    }

    @CacheEvict(keyGenerator = "DefaultGenerator")
    public ResponseEntity<Void> deleteUserSimpleByName(String name) {
        return repository.existsById(name) ? getResult(name) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Void> getResult(String name) {
        repository.deleteUserSimpleDOByNameEquals(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
