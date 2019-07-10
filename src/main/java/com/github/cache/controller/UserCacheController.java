package com.github.cache.controller;

import com.github.cache.pojo.UserCacheDO;
import com.github.cache.service.UserCacheService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 创建时间为 19:04 2019-07-07
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
public class UserCacheController {

    @Resource
    private UserCacheService service;

    @GetMapping("cache/name/{name}")
    public UserCacheDO findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @GetMapping("cache/phone/{phone}")
    public UserCacheDO findByPhone(@PathVariable String phone) {
        return service.findByPhone(phone);
    }

    @PostMapping("cache")
    public UserCacheDO create(@RequestBody UserCacheDO userCacheDO) {
        return service.create(userCacheDO);
    }

    @PutMapping("cache")
    public UserCacheDO update(@RequestBody UserCacheDO userCacheDO) {
        return service.update(userCacheDO);
    }

    @DeleteMapping("cache/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        return service.delete(name);
    }

}
