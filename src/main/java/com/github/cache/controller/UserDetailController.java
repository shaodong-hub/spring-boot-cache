package com.github.cache.controller;

import com.github.cache.pojo.ReturnDO;
import com.github.cache.pojo.UserDetailDO;
import com.github.cache.service.UserDetailService;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
public class UserDetailController {

    @Resource
    private UserDetailService service;

    /**
     * 根据用户名查询用户信息
     *
     * @param name 用户名
     * @return UserDetailDO
     */
    @GetMapping("/detail/name/{name}")
    public ReturnDO<UserDetailDO> findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    /**
     * 根据手机号码查询用户
     *
     * @param phone 手机号码
     * @return UserDetailDO
     */
    @GetMapping("/detail/phone/{phone}")
    public ReturnDO<UserDetailDO> findByPhone(@PathVariable String phone) {
        return service.findByPhone(phone);
    }

    /**
     * 查询所有的用户
     *
     * @param pageable 分页信息
     * @return Page
     */
    @GetMapping("/detail")
    public Page<UserDetailDO> findAll(@PageableDefault(size = 4, page = 0, sort = "name,asc") Pageable pageable) {
        return service.findAll(pageable);
    }

    /**
     * 新增用户
     *
     * @param userDetailDO 用户信息
     * @return UserDetailDO
     */
    @PostMapping("/detail")
    public ReturnDO<UserDetailDO> create(@RequestBody UserDetailDO userDetailDO) {
        return service.create(userDetailDO);
    }

    /**
     * 修改用户信息
     *
     * @param userDetailDO 用户信息
     * @return UserDetailDO
     */
    @PutMapping("/detail")
    public ReturnDO<UserDetailDO> update(@RequestBody UserDetailDO userDetailDO) {
        return service.update(userDetailDO);
    }

    /**
     * @param name 根据用户名删除用户
     * @return ResponseEntity
     */
    @DeleteMapping("/detail/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        return service.delete(name);
    }

    /**
     * 删除所有的用户
     *
     * @return ResponseEntity
     */
    @DeleteMapping("/detail")
    public ResponseEntity<Void> deleteAll() {
        return service.deleteAll();
    }


}
