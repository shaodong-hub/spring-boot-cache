package com.github.cache.controller.impl;

import com.github.cache.controller.IRoleDetailController;
import com.github.cache.pojo.doo.RoleDetailDO;
import com.github.cache.service.IRoleDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 创建时间为 下午3:29 2019/11/27
 * 项目名称 spring-boot-cache
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@RestController
public class RoleDetailControllerImpl implements IRoleDetailController {

    @Resource
    private IRoleDetailService service;

    /**
     * @param id
     * @return
     */
    @GetMapping("/role/{id}")
    @Override
    public RoleDetailDO getRoleDetailDO(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * @param map
     * @return
     */
    @PostMapping("/map")
    public Map<String, String> getMap(@RequestBody Map<String, String> map) {
        return map;
    }


}
