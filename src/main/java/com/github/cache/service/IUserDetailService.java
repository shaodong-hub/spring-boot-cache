package com.github.cache.service;

import com.github.cache.pojo.doo.RoleDetailDO;
import com.github.cache.pojo.doo.UserDetailDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * <p>
 * 创建时间为 上午12:45 2019/10/14
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

public interface IUserDetailService {

    /**
     * 根据用户名查询用户
     *
     * @param name 请求的用户名
     * @return UserDetailDO
     */
    UserDetailDO findByName(String name);

    /**
     * 根据手机号码查询用户
     *
     * @param phone 用户手机号码
     * @return UserDetailDO
     */
    UserDetailDO findByPhone(String phone);

    /**
     * 获取所有的用户信息
     *
     * @param pageable 分页信息
     * @return Page
     */
    Page<UserDetailDO> findAll(Pageable pageable);

    /**
     * -@CachePut:既调用方法,又更新缓存: 修改了数据库的某个数据, 同步更新缓存
     * 运行时机:
     * 1.先调用目标方法;
     * 2.将目标方法的结果缓存起来;
     *
     * @param userCacheDTO UserDetailDO
     * @return UserDetailDO
     */
    UserDetailDO create(UserDetailDO userCacheDTO);

    /**
     * 更新用户信息
     *
     * @param userCacheDTO 用户信息
     * @return UserDetailDO
     */
    UserDetailDO update(UserDetailDO userCacheDTO);

    /**
     * -@CacheEvict
     *
     * @param name name
     */
    ResponseEntity<Void> delete(String name);

    /**
     * 删除所有的数据
     *
     * @return void
     */
    ResponseEntity<Void> deleteAll();

}
