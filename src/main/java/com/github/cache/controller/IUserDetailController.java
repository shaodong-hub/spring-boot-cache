package com.github.cache.controller;

import com.github.cache.pojo.doo.UserDetailDO;
import com.github.cache.pojo.dto.ReturnDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 创建时间为 上午12:27 2019/10/14
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */
public interface IUserDetailController {

    /**
     * 根据用户名查询用户信息
     *
     * @param name 用户名
     * @return UserDetailDO
     */
    ReturnDTO<UserDetailDO> findByName(@PathVariable String name);

    /**
     * 根据手机号码查询用户
     *
     * @param phone 手机号码
     * @return UserDetailDO
     */
    ReturnDTO<UserDetailDO> findByPhone(@PathVariable String phone);

    /**
     * 查询所有的用户
     *
     * @param pageable 分页信息
     * @return Page
     */
    Page<UserDetailDO> findAll(@PageableDefault(size = 4, page = 0, sort = "name,asc")
                                       Pageable pageable);

    /**
     * 新增用户
     *
     * @param userDetailDO 用户信息
     * @return UserDetailDO
     */
    ReturnDTO<UserDetailDO> create(@RequestBody UserDetailDO userDetailDO);

    /**
     * 修改用户信息
     *
     * @param userDetailDO 用户信息
     * @return UserDetailDO
     */
    ReturnDTO<UserDetailDO> update(@RequestBody UserDetailDO userDetailDO);

    /**
     * 删除单个用户
     *
     * @param name 根据用户名删除用户
     * @return ResponseEntity
     */
    ResponseEntity<Void> delete(@PathVariable String name);

    /**
     * 删除所有的用户
     *
     * @return ResponseEntity
     */
    ResponseEntity<Void> deleteAll();

}
