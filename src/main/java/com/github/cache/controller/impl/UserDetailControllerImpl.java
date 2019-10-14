package com.github.cache.controller.impl;

import com.github.cache.controller.IUserDetailController;
import com.github.cache.pojo.ReturnDTO;
import com.github.cache.pojo.UserDetailDO;
import com.github.cache.service.impl.UserDetailServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class UserDetailControllerImpl implements IUserDetailController {

    @Resource
    private UserDetailServiceImpl service;

    @GetMapping("/detail/name/{name}")
    @Override
    public ReturnDTO<UserDetailDO> findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @GetMapping("/detail/phone/{phone}")
    @Override
    public ReturnDTO<UserDetailDO> findByPhone(@PathVariable String phone) {
        return service.findByPhone(phone);
    }

    @GetMapping("/detail")
    @Override
    public Page<UserDetailDO> findAll(@PageableDefault(size = 4, page = 0, sort = "name", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.findAll(pageable);
    }

    @PostMapping("/detail")
    @Override
    public ReturnDTO<UserDetailDO> create(@RequestBody UserDetailDO userDetailDO) {
        return service.create(userDetailDO);
    }

    @PutMapping("/detail")
    @Override
    public ReturnDTO<UserDetailDO> update(@RequestBody UserDetailDO userDetailDO) {
        return service.update(userDetailDO);
    }

    @DeleteMapping("/detail/{name}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable String name) {
        return service.delete(name);
    }

    @DeleteMapping("/detail")
    @Override
    public ResponseEntity<Void> deleteAll() {
        return service.deleteAll();
    }

}
