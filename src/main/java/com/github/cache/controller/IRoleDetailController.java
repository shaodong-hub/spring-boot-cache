package com.github.cache.controller;

import com.github.cache.pojo.doo.RoleDetailDO;

/**
 * <p>
 * 创建时间为 下午3:28 2019/11/27
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

public interface IRoleDetailController {

    /**
     * @param id
     * @return
     */
    RoleDetailDO getRoleDetailDO(Long id);

}
