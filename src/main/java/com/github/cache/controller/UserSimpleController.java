package com.github.cache.controller;

import com.github.cache.pojo.UserSimpleDO;
import com.github.cache.service.UserSimpleService;
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
 * 创建时间为 21:42 2019-07-09
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
public class UserSimpleController {

    @Resource
    private UserSimpleService service;

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return UserSimpleDO
     */
    @GetMapping("/simple/{name}")
    public UserSimpleDO getUserSimpleByName(@PathVariable String name) {
        return service.getUserSimpleByName(name);
    }

    /**
     * 新增用户
     *
     * @param userSimpleDO UserSimpleDO
     * @return UserSimpleDO
     */
    @PostMapping("/simple")
    public UserSimpleDO createUserSimple(@RequestBody UserSimpleDO userSimpleDO) {
        return service.createUserSimple(userSimpleDO);
    }

    /**
     * 修改用户
     *
     * @param userSimpleDO UserSimpleDO
     * @return UserSimpleDO
     */
    @PutMapping("/simple")
    public UserSimpleDO updateUserSimple(@RequestBody UserSimpleDO userSimpleDO) {
        return service.updateUserSimple(userSimpleDO);
    }

    /**
     * 根据用户名删除用户
     *
     * @param name 用户名
     * @return 删除用户
     */
    @DeleteMapping("/simple/{name}")
    public ResponseEntity<Void> deleteUserSimpleByName(@PathVariable String name) {
        return service.deleteUserSimpleByName(name);
    }

}
