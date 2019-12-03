package com.github.cache.service;

import com.github.cache.pojo.doo.RoleDetailDO;

/**
 * <p>
 * 创建时间为 下午3:25 2019/11/27
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

public interface IRoleDetailService {

    RoleDetailDO findById(Long id);

}
