package com.github.cache.repository;

import com.github.cache.pojo.UserSimpleDO;
import org.springframework.data.repository.PagingAndSortingRepository;

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

public interface IUserSimpleRepository extends PagingAndSortingRepository<UserSimpleDO, String> {


    /**
     * 通过用户名删除用户
     *
     * @param name 用户名
     */
    void deleteUserSimpleDOByNameEquals(String name);



}
