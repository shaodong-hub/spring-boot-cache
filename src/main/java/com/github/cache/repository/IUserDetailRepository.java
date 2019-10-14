package com.github.cache.repository;

import com.github.cache.pojo.UserDetailDO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * <p>
 * 创建时间为 18:54 2019-07-07
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

public interface IUserDetailRepository extends PagingAndSortingRepository<UserDetailDO, String> {


    /**
     * 根据用户名查询单个用户
     *
     * @param name 用户名
     * @return UserDetailDO
     */
    UserDetailDO findByNameEquals(String name);

    /**
     * 根据手机号码查询单个用户
     *
     * @param phone 手机号码
     * @return UserDetailDO
     */
    UserDetailDO findUserCacheDOByPhoneEquals(String phone);


    /**
     * 删除单个用户
     *
     * @param name 用户名
     */
    void deleteUserCacheDOByNameIs(String name);

}