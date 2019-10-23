package com.github.cache.controller.impl;

import com.github.cache.controller.IUserDetailController;
import com.github.cache.pojo.dto.ReturnDTO;
import com.github.cache.pojo.doo.UserDetailDO;
import com.github.cache.service.IUserDetailService;
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
    private IUserDetailService service;

    @Override
    @GetMapping("/detail/name/{name}")
    public ReturnDTO<UserDetailDO> findByName(@PathVariable String name) {
        return ReturnDTO.<UserDetailDO>builder().status(0).data(service.findByName(name)).build();
    }

    @Override
    @GetMapping("/detail/phone/{phone}")
    public ReturnDTO<UserDetailDO> findByPhone(@PathVariable String phone) {
        return ReturnDTO.<UserDetailDO>builder().status(0).data(service.findByPhone(phone)).build();
    }

    @Override
    @GetMapping("/detail")
    public Page<UserDetailDO> findAll(@PageableDefault(size = 4, page = 0, sort = "name", direction = Sort.Direction.DESC)
                                              Pageable pageable) {
        return service.findAll(pageable);
    }

    @Override
    @PostMapping("/detail")
    public ReturnDTO<UserDetailDO> create(@RequestBody UserDetailDO userDetailDO) {
        return ReturnDTO.<UserDetailDO>builder().status(0).data(service.create(userDetailDO)).build();
    }

    @Override
    @PutMapping("/detail")
    public ReturnDTO<UserDetailDO> update(@RequestBody UserDetailDO userDetailDO) {
        return ReturnDTO.<UserDetailDO>builder().status(0).data(service.update(userDetailDO)).build();
    }

    @Override
    @DeleteMapping("/detail/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        return service.delete(name);
    }

    @Override
    @DeleteMapping("/detail")
    public ResponseEntity<Void> deleteAll() {
        return service.deleteAll();
    }

}
