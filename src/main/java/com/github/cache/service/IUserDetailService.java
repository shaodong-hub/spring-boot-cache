package com.github.cache.service;

import com.github.cache.pojo.ReturnDTO;
import com.github.cache.pojo.UserDetailDO;
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
     * @param name 请求的用户名
     * @return UserDetailDO
     */
    ReturnDTO<UserDetailDO> findByName(String name);

    ReturnDTO<UserDetailDO> findByPhone(String phone);

    /**
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
    ReturnDTO<UserDetailDO> create(UserDetailDO userCacheDTO);

    ReturnDTO<UserDetailDO> update(UserDetailDO userCacheDTO);

    /**
     * -@CacheEvict
     *
     * @param name name
     */
    ResponseEntity<Void> delete(String name);

    ResponseEntity<Void> deleteAll();

}
